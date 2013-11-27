---
layout: post
title:  "Out of memory issue"
date:   2013-11-27 10:00:00
categories: faq issues
---

By default, OpenSearchServer uses only 256 megabytes of RAM. This value is not enough for advanced use. To allow OSS to use more memory, you have to add two lines in the start.sh file

## Allow more RAM

**For Linux/Mac (start.sh)**

{% highlight bash %}
  
OPENSEARCHSERVER_DATA=`pwd`/data
export OPENSEARCHSERVER_DATA 
CATALINA_OPTS="-Xms2G -Xmx2G -server"
export CATALINA_OPTS
exec "$EXECUTABLE"
   
{% endhighlight %}

**For Windows(start.bat)**

{% highlight bash %}
  
set OPENSEARCHSERVER_DATA=%cd%\data
set CATALINA_OPTS=-server -Xms2G -Xmx2G
cd %cd%\apache-tomcat-7.0.37
call "%EXECUTABLE%"
  
{% endhighlight %}

The memory allocated to OSS is defined by the CATALINA_OPTS options.

`-Xms2G -Xmx2G` means 2 GB of RAM.
You may use `-Xms768m -Xmx768m` to allow 768 megabytes of RAM.

## More than 2GB of RAM

You have to run OpenSearchServer in 64 bits mode to use more than 2 gigabytes.

The first requirement is to run into a 64 bits operating system and to have installed a 64 bits Java runtime.

Then you have to modify the start.sh file as is:

{% highlight bash %}
  
OPENSEARCHSERVER_DATA=`pwd`/data
export OPENSEARCHSERVER_DATA
CATALINA_OPTS="-d64 -Xms6G -Xmx6G -server"
export CATALINA_OPTS
exec "$EXECUTABLE"
  
{% endhighlight %}

Usual `Xms` and `Xmx` parameters control the memory (6 gigabytes in our example).

The `-d64` parameter enables the 64 bits operations.

## What size of RAM is used/available?

Look at the **free memory rate** value of the monitoring panel to know if OpenSearchServer is comfortably running. Go to the `/Runtime/System/General` panel. A value bigger than 20% is recommended.

![Memory usage]({{ site.baseurl }}/assets/faq/outofmemory.png)