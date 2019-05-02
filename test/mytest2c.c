#include "syscall.h"
#include "stdio.h"

char buf[100];
int main()
{
	while(1)
	{
		int f=open("pool");
		read(f,buf,100);
		printf("%s\n",buf);
		close(f);
	}
	return 0;
}

