#include "syscall.h"
#include "stdio.h"

char buf[100];
char* tt[]={"hello!","welcome!"};
int main()
{
	printf("pid=%d\n",exec("mytest2a.coff",0,0));
	printf("pid=%d\n",exec("mytest2b.coff",0,0));
	printf("pid=%d\n",exec("mytest2d.coff",2,tt));
	//exec("mytest2c.coff",0,0);
	while(1);
	return 0;
	/*
	int ff=creat("data");
	close(ff);
	write(1,"10 200\n",strlen("10 200\n"));
	printf("%d\n",write(ff,"10 200\n",10));
	halt();
	return 0;
	*/

	/*
	int a=0,b=0,i=0;
	int ff=open("test");
	int l=read(ff,buf,100);
	printf("%d\n",l);
	printf("%s\n",buf);
	for(;;i++)
	{
		if(buf[i]<'0'||buf[i]>'9') break;
		a=10*a+buf[i]-'0';
	}
	for(;;i++)
	{
		if(!(buf[i]<'0'||buf[i]>'9')) break;
	}
	for(;i<l;i++)
	{
		if(buf[i]<'0'||buf[i]>'9') break;
		b=10*b+buf[i]-'0';
	}
	printf("%d+%d=%d\n",a,b,a+b);
	halt();
	return 0;
	*/
}

