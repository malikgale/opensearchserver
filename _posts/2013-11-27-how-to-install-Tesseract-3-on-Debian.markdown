---
layout: post
title:  "How to install Tesseract 3 on Debian"
date:   2013-11-27 10:00:00
categories: faq installation
---

By default, Debian provide only the version 2.x of Tesseract. OpenSearchServer requires Tesseract 3. Here is how to setup Tesseract 3.x using the sources.

**Install the compiler tools: g++ and make**

{% highlight bash %}
  
root@ssd2:~# apt-get install g++
root@ssd2:~# apt-get install make
  
{% endhighlight %}

**Download, compile and install Leptonica**

[http://www.leptonica.com/download.html](http://www.leptonica.com/download.html)

We use a standard installation in /usr/local

{% highlight bash %}
root@ssd2:~# wget http://www.leptonica.com/source/leptonica-1.69.tar.gz
root@ssd2:~# gunzip leptonica-1.69.tar.gz
root@ssd2:~# tar -xvf leptonica-1.69.tar
root@ssd2:~# cd leptonica-1.69
root@ssd2:~/leptonica-1.69# ./configure
root@ssd2:~/leptonica-1.69# make
root@ssd2:~/leptonica-1.69# make install
{% endhighlight %}

**Download, compile and install Tesseract 3.x**

[http://code.google.com/p/tesseract-ocr/](http://code.google.com/p/tesseract-ocr/)

We use a standard installation in /usr/local

{% highlight bash %}
root@ssd2:~# wget http://tesseract-ocr.googlecode.com/files/tesseract-ocr-3.02.02.tar.gz
root@ssd2:~# gunzip tesseract-ocr-3.02.02.tar.gz
root@ssd2:~# tar -xvf tesseract-ocr-3.02.02.tar
root@ssd2:~# cd tesseract-ocr
root@ssd2:~/tesseract-ocr# ./configure
root@ssd2:~/tesseract-ocr# make
root@ssd2:~/tesseract-ocr# make install
{% endhighlight %}

**Download and install the languages you need**

{% highlight bash %}
root@ssd2:~# wget http://tesseract-ocr.googlecode.com/files/tesseract-ocr-3.02.eng.tar.gz
root@ssd2:~# wget http://tesseract-ocr.googlecode.com/files/tesseract-ocr-3.02.spa.tar.gz
root@ssd2:~# wget http://tesseract-ocr.googlecode.com/files/tesseract-ocr-3.02.ita.tar.gz
root@ssd2:~# wget http://tesseract-ocr.googlecode.com/files/tesseract-ocr-3.02.deu.tar.gz
root@ssd2:~# wget http://tesseract-ocr.googlecode.com/files/tesseract-ocr-3.02.fra.tar.gz
root@ssd2:~# gunzip tesseract-ocr-3.02.???.tar.gz
root@ssd2:~# tar -xvf tesseract-ocr-3.02.eng.tar
root@ssd2:~# tar -xvf tesseract-ocr-3.02.spa.tar
root@ssd2:~# tar -xvf tesseract-ocr-3.02.ita.tar
root@ssd2:~# tar -xvf tesseract-ocr-3.02.deu.tar
root@ssd2:~# tar -xvf tesseract-ocr-3.02.fra.tar
root@ssd2:~# cd tesseract-ocr/tessdata/
root@ssd2:~/tesseract-ocr/tessdata# cp *.traineddata /usr/local/share/tessdata/
{% endhighlight %}

**Setup the appropriate PATH and LD_LIBRARY_PATH and testing**

{% highlight bash %}
root@ssd2:~/tesseract-ocr/tessdata# export PATH=$PATH:/usr/local/bin
root@ssd2:~/tesseract-ocr/tessdata# export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/usr/local/lib
root@ssd2:~/tesseract-ocr/tessdata# tesseract --list-langs
List of available languages (10):
dan-frak
deu-frak
deu
eng
fra
ita
ita_old
slk-frak
spa
spa_old
{% endhighlight %}

You should put the environment variable definition in OpenSearchServer start script.

**Restart OpenSearchServer, and setup the advanced tab panel**