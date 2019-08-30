//
// Created by HuangQiang on 2019/5/9.
//

#ifndef HOMEWORK_TOOLS_H
#define HOMEWORK_TOOLS_H


#include <stdio.h>
#include <sys/time.h>
long getCurrentTime()
{
    struct timeval tv;
    gettimeofday(&tv,NULL);
    return tv.tv_sec * 1000 + tv.tv_usec / 1000;
}

double Round(double dSrc, int iBit)
{
    double retVal 	= 0.0;
    int  intTmp		= 0;
    if ( 0 > iBit )
    {
        return 0;
    }
    if (0 > dSrc)
    {
        // 首先转为正数
        dSrc *= -1;

        intTmp = (int)((dSrc + 0.5 / pow(10.0, iBit)) * pow(10.0, iBit));
        retVal = (double)intTmp / pow(10.0, iBit);

        // 再转为 负数
        retVal *= -1;
    }
    else
    {
        intTmp = (int)((dSrc + 0.5 / pow(10.0, iBit)) * pow(10.0, iBit));
        retVal = (double)intTmp / pow(10.0, iBit);
    }
    return retVal;
}

time_t StringToDatetime(std::string str)
{
    char *cha = (char*)str.data();
    tm tm_;
    int year, month, day, hour, minute, second;
    sscanf(cha, "%d-%d-%d %d:%d:%d", &year, &month, &day, &hour, &minute, &second);
    tm_.tm_year = year - 1900;
    tm_.tm_mon = month - 1;
    tm_.tm_mday = day;
    tm_.tm_hour = hour;
    tm_.tm_min = minute;
    tm_.tm_sec = second;
    tm_.tm_isdst = 0;
    time_t t_ = mktime(&tm_);
    return t_;
}


#endif //HOMEWORK_TOOLS_H
