package nachos.threads;

import nachos.machine.*;

import java.util.TreeSet;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

/**
 * A scheduler that chooses threads using a lottery.
 *
 * <p>
 * A lottery scheduler associates a number of tickets with each thread. When a
 * thread needs to be dequeued, a random lottery is held, among all the tickets
 * of all the threads waiting to be dequeued. The thread that holds the winning
 * ticket is chosen.
 *
 * <p>
 * Note that a lottery scheduler must be able to handle a lot of tickets
 * (sometimes billions), so it is not acceptable to maintain state for every
 * ticket.
 *
 * <p>
 * A lottery scheduler must partially solve the priority inversion problem; in
 * particular, tickets must be transferred through locks, and through joins.
 * Unlike a priority scheduler, these tickets add (as opposed to just taking
 * the maximum).
 */
public class LotteryScheduler extends PriorityScheduler {
    /**
     * Allocate a new lottery scheduler.
     */
    public LotteryScheduler()
	{
		num_tickets=0;
		rng=new Random();
		thread_original_tickets=new HashMap<KThread,Integer>();
		thread_effective_tickets=new HashMap<KThread,Integer>();
		upstream=new HashMap<KThread,HashMap<KThread,Integer>>();
    }

	public int getPriority(KThread thread)
	{
		Lib.assertTrue(Machine.interrupt().disabled());
		init_thread_tickets(thread);
		return thread_original_tickets.get(thread);
	}

	public int getEffectivePriority(KThread thread)
	{
		Lib.assertTrue(Machine.interrupt().disabled());
		init_thread_tickets(thread);
		return thread_effective_tickets.get(thread);
	}

    public void setPriority(KThread thread,int priority)
	{
		Lib.assertTrue(Machine.interrupt().disabled()); 
		Lib.assertTrue(priority>=min_tickets&&priority<=max_tickets);
		init_thread_tickets(thread);
		update(thread,priority-thread_original_tickets.get(thread));
    }
	
	public boolean increasePriority()
	{
		boolean intStatus = Machine.interrupt().disable();
		KThread thread = KThread.currentThread();
		int priority=getPriority(thread);
		if(priority==priorityMaximum)
			return false;
		update(thread,1);
		Machine.interrupt().restore(intStatus);
		return true;
	}

	public boolean decreasePriority()
	{
		boolean intStatus = Machine.interrupt().disable();
		KThread thread = KThread.currentThread();
		int priority=getPriority(thread);
		if(priority==priorityMinimum)
			return false;
		update(thread,-1);
		Machine.interrupt().restore(intStatus);
		return true;
	}
    
    /**
     * Allocate a new lottery thread queue.
     *
     * @param	transferPriority	<tt>true</tt> if this queue should
     *					transfer tickets from waiting threads
     *					to the owning thread.
     * @return	a new lottery thread queue.
     */
    public ThreadQueue newThreadQueue(boolean transferPriority)
	{
		return new lottery_queue(transferPriority);
    }

	protected void init_thread_tickets(KThread x)
	{
		if(!thread_original_tickets.containsKey(x))
		{
			num_tickets+=1;
			thread_original_tickets.put(x,1);
			thread_effective_tickets.put(x,1);
			upstream.put(x,new HashMap<KThread,Integer>());
		}
	}

	protected void update(KThread x,int amount)
	{
		//System.out.println("Update effective tickets by "+amount+": "+x);
		num_tickets+=amount;
		thread_effective_tickets.put(x,thread_effective_tickets.get(x)+amount);
		HashMap<KThread,Integer> tmp=upstream.get(x);
		for(KThread y:tmp.keySet())
			if(tmp.get(y)!=0)
				update(y,tmp.get(y)*amount);
	}

	protected void add_upstream(KThread x,KThread y)
	{
		update(y,thread_effective_tickets.get(x));
		if(!upstream.get(x).containsKey(y))
			upstream.get(x).put(y,1);
		else
			upstream.get(x).put(y,upstream.get(x).get(y)+1);
	}

	protected void remove_upstream(KThread x,KThread y)
	{
		update(y,-thread_effective_tickets.get(x));
		upstream.get(x).put(y,upstream.get(x).get(y)-1);
	}

	protected class lottery_queue extends ThreadQueue
	{
		public lottery_queue(boolean transfer)
		{
			this.transfer=transfer;
			acquiring_process=null;
			waiting_queue=new HashSet<KThread>();
		}
		public void waitForAccess(KThread thread)
		{
			init_thread_tickets(thread);
			waiting_queue.add(thread);
			if(transfer&&acquiring_process!=null)
				add_upstream(thread,acquiring_process);
		}
		public void acquire(KThread thread)
		{
			init_thread_tickets(thread);
			acquiring_process=thread;
			if(transfer)
				for(KThread x:waiting_queue)
					add_upstream(x,thread);
		}
		public KThread nextThread()
		{
			if(waiting_queue.isEmpty())
			{
				acquiring_process=null;
				return null;
			}
			KThread result=null;
			int waiting_sum=0;
			for(KThread x:waiting_queue)
				waiting_sum+=thread_effective_tickets.get(x);
			int win=rng.nextInt(waiting_sum),acc=0;
			for(KThread x:waiting_queue)
				if(acc<=win&&win<acc+thread_effective_tickets.get(x))
				{
					result=x;
					break;
				}
				else
					acc+=thread_effective_tickets.get(x);

			if(transfer&&acquiring_process!=null)
				for(KThread x:waiting_queue)
					remove_upstream(x,acquiring_process);
			waiting_queue.remove(result);
			acquiring_process=result;
			if(transfer)
				for(KThread x:waiting_queue)
					add_upstream(x,acquiring_process);
			return result;
		}
		public void print()
		{
		}
		public boolean transfer;
		private KThread acquiring_process;
		private HashSet<KThread> waiting_queue;
	}

	protected int num_tickets=0;
	protected static HashMap<KThread,Integer> thread_original_tickets;
	protected static HashMap<KThread,Integer> thread_effective_tickets;
	protected static HashMap<KThread,HashMap<KThread,Integer>> upstream;
	public static final int min_tickets=1;
	public static final int max_tickets=Integer.MAX_VALUE;
	protected static Random rng;
}

