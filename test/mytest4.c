#include "syscall.h"
#include "stdio.h"

int main()
{
	int i=0;
	for(i=0;i<10000;i++)
	{
		if(i%10000==0)
			printf("i=%d\n",i);
	}
	return 12;
}

