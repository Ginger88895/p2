#include "syscall.h"
#include "stdio.h"

char buf[100];
char* tt[]={"hello!","welcome!"};
int main()
{
	int cid,tmp;
	cid=exec("mytest4.coff",0,0);
	printf("pid=%d\n",cid);
	join(cid,&tmp);
	cid=exec("mytest4.coff",0,0);
	printf("pid=%d\n",cid);
	join(cid,&tmp);
	cid=exec("mytest4.coff",0,0);
	printf("pid=%d\n",cid);
	join(cid,&tmp);
	cid=exec("mytest4.coff",0,0);
	printf("pid=%d\n",cid);
	join(cid,&tmp);
	cid=exec("mytest4.coff",0,0);
	printf("pid=%d\n",cid);
	join(cid,&tmp);
	cid=exec("mytest4.coff",0,0);
	printf("pid=%d\n",cid);
	join(cid,&tmp);
	cid=exec("mytest4.coff",0,0);
	printf("pid=%d\n",cid);
	join(cid,&tmp);
	cid=exec("mytest4.coff",0,0);
	printf("pid=%d\n",cid);
	join(cid,&tmp);
	cid=exec("mytest4.coff",0,0);
	printf("pid=%d\n",cid);
	join(cid,&tmp);
	cid=exec("mytest4.coff",0,0);
	printf("pid=%d\n",cid);
	join(cid,&tmp);
	cid=exec("mytest4.coff",0,0);
	printf("pid=%d\n",cid);
	join(cid,&tmp);
	cid=exec("mytest4.coff",0,0);
	printf("pid=%d\n",cid);
	join(cid,&tmp);
	cid=exec("mytest4.coff",0,0);
	printf("pid=%d\n",cid);
	join(cid,&tmp);
	cid=exec("mytest4.coff",0,0);
	printf("pid=%d\n",cid);
	join(cid,&tmp);
	cid=exec("mytest4.coff",0,0);
	printf("pid=%d\n",cid);
	join(cid,&tmp);
	cid=exec("mytest4.coff",0,0);
	printf("pid=%d\n",cid);
	join(cid,&tmp);
	cid=exec("mytest4.coff",0,0);
	printf("pid=%d\n",cid);
	join(cid,&tmp);
	cid=exec("mytest4.coff",0,0);
	printf("pid=%d\n",cid);
	join(cid,&tmp);
	cid=exec("mytest4.coff",0,0);
	printf("pid=%d\n",cid);
	join(cid,&tmp);
	cid=exec("mytest4.coff",0,0);
	printf("pid=%d\n",cid);
	join(cid,&tmp);
	cid=exec("mytest4.coff",0,0);
	printf("pid=%d\n",cid);
	join(cid,&tmp);
	halt();
	return 0;
}

