//
// Created by HuangQiang on 2019/5/19.
//

#ifndef HOMEWORK_CPPLAB_H
#define HOMEWORK_CPPLAB_H

#include <iostream>
#include <complex>
#include <vector>
#include <fstream>
#include "Tools.h"
#include <iomanip>
//不该在这用命名空间，懒得改，就这样吧。
using  namespace std;
//*************************************************************************************
//第一个实验。
vector<complex<double>>  getRoot(int a,int b,int c){
    cout<<a<<"*(X^2)+"<<b<<"*x+"<<c<<"=0"<<endl;
    vector<complex<double>> roots;
    int temp=b*b-4*a*c;
    if (temp>=0){
        double root_0=(-1.0*b+sqrt(temp))/(2*a);
        double root_1=(-1.0*b-sqrt(temp))/(2*a);
        roots.push_back(complex<double>(root_0,0));
        roots.push_back(complex<double>(root_1,0));
        return roots;
    } else{
        complex<double> root_0(-1.0*b/(2*a),sqrt(-temp)/(2*a));
        complex<double> root_1(-1.0*b/(2*a),-sqrt(-temp)/(2*a));
        roots.push_back(root_0);
        roots.push_back(root_1);
        return roots;
    }
}
//*************************************************************************************
//第二个实验
void fileStream ()
{
    string data;
    ofstream outfile;
    outfile.open("../Data/randomNum.dat");
    for (int i = 0; i < 10; ++i) {
        int random_0=(rand() % (100))+ 1;
        int random_1=(rand() % (100))+ 1;
        outfile << random_0<<endl<<random_1 << endl;
    }
    outfile.close();
    cout<<"选择运算：1：加；2：减；3：乘；4：除(结果保留两位小数)"<<endl;
    int operation;
    double result;
    cin>>operation;
    ifstream infile;
    infile.open("../Data/randomNum.dat");
    infile>>data;
    stringstream ss;
    int num_0 = std::atoi( data.c_str() );
    infile >> data;
    int num_1 = std::atoi( data.c_str() );

    switch (operation){
        case 1:
        {
            cout<<num_0<<"+"<<num_1<<"=";
            cin>>result;
            double correctAnswer=num_0+num_1;
            if (correctAnswer==result)
            {
                cout<<"正确"<<endl;
            } else{
                cout<<"错误,正确答案："<<correctAnswer<<endl;
            }
        }
            break;
        case 2:
        {
            cout<<num_0<<"-"<<num_1<<"=";
            cin>>result;
            double correctAnswer=num_0-num_1;
            if (correctAnswer==result)
            {
                cout<<"正确"<<endl;
            } else{
                cout<<"错误,正确答案："<<correctAnswer<<endl;
            }
        }
            break;
        case 3:
        {
            cout<<num_0<<"*"<<num_1<<"=";
            cin>>result;
            double correctAnswer=num_0*num_1;
            if (correctAnswer==result)
            {
                cout<<"正确"<<endl;
            } else{
                cout<<"错误,正确答案："<<correctAnswer<<endl;
            }
        }
            break;
        case 4:
        {
            cout<<num_0<<"/"<<num_1<<"=";
            cin>>result;
            double correctAnswer=Round((1.0*num_0)/(num_1),2);
            if (correctAnswer==result)
            {
                cout<<"正确"<<endl;
            } else{
                cout<<"错误,正确答案："<<correctAnswer<<endl;
            }
        }
            break;
    }
    infile.close();
}
//*************************************************************************************
//第三个实验。
class Student  {
public:
    Student( const string &name,int stuNum, int classNum, int englishScore, int mathScore, int computerScore);

    int getStuNum() const;

    void setStuNum(int stuNum);

    int getClassNum() const;

    void setClassNum(int classNum);

    int getEnglishScore() const;

    void setEnglishScore(int englishScore);

    int getMathScore() const;

    void setMathScore(int mathScore);

    int getComputerScore() const;

    void setComputerScore(int computerScore);

    const string &getName() const;

    void setName(const string &name);

    virtual ~Student();


private:
    int stuNum,classNum,englishScore,mathScore,computerScore;
    string name;
};

Student::Student( const string &name,int stuNum, int classNum, int englishScore, int mathScore, int computerScore)
        : stuNum(stuNum), classNum(classNum), englishScore(englishScore), mathScore(mathScore),
          computerScore(computerScore), name(name) {}

int Student::getStuNum() const {
    return stuNum;
}

void Student::setStuNum(int stuNum) {
    Student::stuNum = stuNum;
}

int Student::getClassNum() const {
    return classNum;
}

void Student::setClassNum(int classNum) {
    Student::classNum = classNum;
}

int Student::getEnglishScore() const {
    return englishScore;
}

void Student::setEnglishScore(int englishScore) {
    Student::englishScore = englishScore;
}

int Student::getMathScore() const {
    return mathScore;
}

void Student::setMathScore(int mathScore) {
    Student::mathScore = mathScore;
}

int Student::getComputerScore() const {
    return computerScore;
}

void Student::setComputerScore(int computerScore) {
    Student::computerScore = computerScore;
}

const string &Student::getName() const {
    return name;
}

void Student::setName(const string &name) {
    Student::name = name;
}

Student::~Student() {

}
void stuStatistics()
{
    void statistics(int length,int score[],int statistics[]);
    void printStatistics( int statistics[]);
    vector<Student> students;
    cout<<"姓名\t学号\t班级\t数学\t英语\t计算机"<<endl;
    for (int i = 0; i < 8; ++i) {
        Student stu(string("stuName"),i,20,(rand() % (101)),(rand() % (101)),(rand() % (101)));
        students.push_back(stu);
       cout<<stu.getName()<<"\t"<<stu.getStuNum()<<"\t"<<stu.getClassNum()<<"\t"<<stu.getMathScore()<<"\t"<<stu.getEnglishScore()<<"\t"<<stu.getComputerScore()<<endl;
    }
    int length=students.size();
    int math[length],english[length],computer[length];

    for (int j = 0; j < students.size(); ++j) {
        Student stu=students[j];
        int mathScore=stu.getMathScore();
        int englishScore=stu.getEnglishScore();
        int computerScore=stu.getComputerScore();
        math[j]=mathScore;
        english[j]=englishScore;
        computer[j]=computerScore;
    }
    int mathStatistics[4],englishStatistics[4],computerStatistics[4];
    for (int k = 0; k < 4; ++k) {
        mathStatistics[k]=0;
        englishStatistics[k]=0;
        computerStatistics[k]=0;
    }
    statistics(length,math,mathStatistics);
    statistics(length,english,englishStatistics);
    statistics(length,computer,computerStatistics);
    cout<<"数学\n";
    printStatistics(mathStatistics);
    cout<<"英语\n";
    printStatistics(englishStatistics);
    cout<<"计算机\n";
    printStatistics(computerStatistics);

}
void statistics(int length,int score[],int statistics[])
{
    for (int k = 0; k < length; ++k) {
        if (score[k] >= 90) {
            ++statistics[3];
        } else {
            if (score[k] >= 80) {
                ++statistics[2];
            } else {
                if (score[k] >= 60) {
                    ++statistics[1];
                } else {
                    ++statistics[0];
                }
            }
        }

    }
}
void printStatistics(int statistics[])
{
    cout<<"优秀："<< statistics[3]<<"\t良好："<<statistics[2]<<"\t"<<"\t及格："<<statistics[1]<<"\t不及格："<<statistics[0]<<endl;
}
//*************************************************************************************
//第四个实验
class MyDate{
public:
    MyDate(int year, int month, int day);

    int getYear() const;

    void setYear(int year);

    int getMonth() const;

    void setMonth(int month);

    int getDay() const;

    void setDay(int day);
    int compare(MyDate date);
    string toString();
    int year,month,day;
};

MyDate::MyDate(int year, int month, int day) : year(year), month(month), day(day) {}

int MyDate::getYear() const {
    return year;
}

void MyDate::setYear(int year) {
    MyDate::year = year;
}

int MyDate::getMonth() const {
    return month;
}

void MyDate::setMonth(int month) {
    MyDate::month = month;
}

int MyDate::getDay() const {
    return day;
}

void MyDate::setDay(int day) {
    MyDate::day = day;
}
string MyDate::toString()
{
    string dateString_0=to_string(MyDate::year)+"-"+to_string(MyDate::month)+"-"+to_string(MyDate::day)+" "+to_string(0)+":"+to_string(0)+" "+to_string(0);
    return dateString_0;
}
int MyDate::compare(MyDate date)
{
    string dateString_0=MyDate::toString();
    string dateString_1=date.toString();
    time_t time_0=StringToDatetime(dateString_0);
    time_t time_1=StringToDatetime(dateString_1);
    int difference=abs(1.0*time_0-time_1)/(24*60*60);
    cout<<"相差"<<time_0-time_1<<"秒 约"<<difference<<"天"<<endl;
    return time_0-time_1;
}
void dateDemo()
{
    MyDate date_0(2019,05,18);
    MyDate date_1(2012,2,2);
    date_0.setDay(20);
    date_1.setYear(2014);
    cout<<date_0.toString()<<"\t"<<date_1.toString()<<endl;
    date_0.compare(date_1);
}
//*************************************************************************************
//第五个实验
enum Timezone {
    ACDT,
    ACST,
    ACT,
    ACWST,
    ADT,
    AEDT,
    AEST,
    AFT,
    AKDT,
    AKST ,
    AMST,
    AMT,
    AST,
    AT,
    AWST,
    AZOST,
    AZOT,
    AZT,
};
class DateWithTimezone:public MyDate
{
public:
    DateWithTimezone(int year, int month, int day, Timezone timezone);

    Timezone getTimezone() const;

    void setTimezone(Timezone timezone);
    string zoneToString();

public:
    Timezone timezone=AZT;

};

DateWithTimezone::DateWithTimezone(int year, int month, int day, Timezone timezone) : MyDate(year, month, day),
                                                                                      timezone(timezone) {}

Timezone DateWithTimezone::getTimezone() const {
    return timezone;
}

void DateWithTimezone::setTimezone(Timezone timezone) {
    DateWithTimezone::timezone = timezone;
}
string DateWithTimezone::zoneToString() {
    string dateString_0=to_string(MyDate::year)+"-"+to_string(MyDate::month)+"-"+to_string(MyDate::day)+" "+to_string(0)+":"+to_string(0)+" "+to_string(0)+to_string(timezone);
    return dateString_0;
}
void dateZone()
{
    DateWithTimezone dateWithTimezone_0(2019,2,2,AZT);
    DateWithTimezone dateWithTimezone_1(2012,2,2,AZT);
    cout<<dateWithTimezone_0.zoneToString()<<"\t"<<dateWithTimezone_1.zoneToString()<<endl;
    dateWithTimezone_0.compare(dateWithTimezone_1);

}
//*************************************************************************************
//第六个实验
class StuWithDate:public Student,public MyDate
{
public:
    StuWithDate(const string &name, int stuNum, int classNum, int englishScore, int mathScore, int computerScore,
                int year, int month, int day);
};

StuWithDate::StuWithDate(const string &name, int stuNum, int classNum, int englishScore, int mathScore,
                         int computerScore, int year, int month, int day) : Student(name, stuNum, classNum,
                                                                                    englishScore, mathScore,
                                                                                    computerScore),
                                                                            MyDate(year, month, day)
{

}
void stuOperation()
{
    void showAll(vector<StuWithDate>stus);
    void add(vector<StuWithDate> &stus);
    void deleteStu(vector<StuWithDate> &stus);
    void edit(vector<StuWithDate> &stus);
    StuWithDate query(vector<StuWithDate>stus);
    vector <StuWithDate> students;
    for (int i = 0; i < 6; ++i) {
        StuWithDate stu(string("stuName"),i,20,(rand() % (101)),(rand() % (101)),(rand() % (101)),1997+rand() % (21),1+rand() % (12),1+rand() % (31));
        students.push_back(stu);
    }
    showAll(students);
    add(students);
    deleteStu(students);
    edit(students);
    showAll(students);
    query(students);
}
void add(vector<StuWithDate> &stus)
{
    cout<<"输入添加学生信息:name, stuNum,classNum,mathScore,englishScore,computerScore,year,month,day;"<<endl;
    string name;
    int stuNum,classNum,mathScore,englishScore,computerScore,year,month,day;
    cin>>name>>stuNum>>classNum>>mathScore>>englishScore>>computerScore>>year>>month>>day;
    StuWithDate stu(name,stuNum,classNum,mathScore,englishScore,computerScore,year,month,day);
    (stus).push_back(stu);
    cout<<"添加成功"<<endl;
}
void showAll(vector<StuWithDate>stus)
{
    cout<<"////////////////////////////////////////////////////////////"<<endl;
    cout<<"name\tstuNum\tclassNum\tmathScore\tenglishScore\tcomputerScore\tyear\tmontht\tday;"<<endl;
    for (int i = 0; i < stus.size(); ++i) {
        StuWithDate stu=stus[i];
        cout<<stu.getName()<<"\t"<<stu.getStuNum()<<"\t"<<stu.getClassNum()<<"\t"<<stu.getMathScore()<<"\t"<<stu.getEnglishScore()<<"\t"<<stu.getComputerScore()<<"\t"
            <<stu.getYear()<<"\t"<<stu.getMonth()<<"\t"<<stu.getDay()<<endl;
    }
}

void deleteStu(vector<StuWithDate>&stus)
{
    showAll(stus);
    int stuNum;
    cout<<"输入要删除学生学号:";
    cin>>stuNum;
    for(vector<StuWithDate>::iterator it  = stus.begin(); it != stus.end(); )
    {
        StuWithDate stu=*(it);
        if (stuNum==stu.getStuNum())
        {
            it = stus.erase(it);
        } else{
            ++it;
        }
    }
}
void edit(vector<StuWithDate> &stus)
{
    showAll(stus);
    int stuNum;
    cout<<"输入要修改学生学号:";
    cin>>stuNum;
    for (int i = 0; i <stus.size() ; ++i)
    {
        StuWithDate &stu=stus[i];

        if (stuNum==stu.getStuNum())
        {
            cout<<"原有信息："<<endl;
            cout<<"name\tstuNum\tclassNum\tmathScore\tenglishScore\tcomputerScore\tyear\tmontht\tday;"<<endl;
            cout<<stu.getName()<<"\t"<<stu.getStuNum()<<"\t"<<stu.getClassNum()<<"\t"<<stu.getMathScore()<<"\t"<<stu.getEnglishScore()<<"\t"<<stu.getComputerScore()<<"\t"
                <<stu.getYear()<<"\t"<<stu.getMonth()<<"\t"<<stu.getDay()<<endl;
            cout<<"输入学生信息:name, stuNum,classNum,mathScore,englishScore,computerScore,year,month,day;"<<endl;
            string name;
            int stuNum,classNum,mathScore,englishScore,computerScore,year,month,day;
            cin>>name>>stuNum>>classNum>>mathScore>>englishScore>>computerScore>>year>>month>>day;
            stu.setName(name);
            stu.setStuNum(stuNum);
            stu.setClassNum(classNum);
            stu.setMathScore(mathScore);
            stu.setComputerScore(computerScore);
            stu.setYear(year);
            stu.setMonth(month);
            stu.setDay(day);
        }
    }
}
StuWithDate query(vector<StuWithDate>stus)
{
    int stuNum;
    cout<<"输入要查询学生学号:";
    cin>>stuNum;
    for (int i = 0; i <stus.size() ; ++i) {
        StuWithDate stu = stus[i];
        if (stuNum == stu.getStuNum()) {
            cout<<stu.getName()<<"\t"<<stu.getStuNum()<<"\t"<<stu.getClassNum()<<"\t"<<stu.getMathScore()<<"\t"<<stu.getEnglishScore()<<"\t"<<stu.getComputerScore()<<"\t"
                <<stu.getYear()<<"\t"<<stu.getMonth()<<"\t"<<stu.getDay()<<endl;
            return stu;
        }
    }
}
#endif //HOMEWORK_CPPLAB_H
