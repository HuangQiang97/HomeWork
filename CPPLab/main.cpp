#include <iostream>
#include <complex>
#include <vector>
#include "Headers/CPPLab.h"

using namespace std;

int   main()
{
    //函数具体实现见：Headers/CPPLab.h
    //第一个实验
    vector<complex<double>> roots=getRoot(4,2,2);
    cout<<roots[0]<<"\t"<<roots[1]<<endl;
    //第二个实验
    cout<<"////////////////////////////////////////////////////////////"<<endl;
    fileStream();
    //第三个实验
    cout<<"////////////////////////////////////////////////////////////"<<endl;
    stuStatistics();
    //第四个实验
    cout<<"////////////////////////////////////////////////////////////"<<endl;
    dateDemo();
    //第五个实验
    cout<<"////////////////////////////////////////////////////////////"<<endl;
    dateZone();
    //第六个实验
    cout<<"////////////////////////////////////////////////////////////"<<endl;
    stuOperation();
}
