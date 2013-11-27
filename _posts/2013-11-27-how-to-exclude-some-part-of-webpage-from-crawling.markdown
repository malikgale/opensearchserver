---
layout: post
title:  "How to exclude some part of webpage from crawling"
date:   2013-11-27 10:00:00
categories: faq
---

OpenSearchServer allows you to exclude certain part of webpage from crawling.

### Excluding content using opensearchserver.ignore CSS class

The content of any HTML tag with the class `opensearchserver.ignore` will be removed while crawling.

The `opensearchserver.ignore` class can also be added with your existing CSS classes.

Example :

{% highlight html %}
  
<div class="opensearchserver.ignore">This content will not be indexed in OpenSearchServer.</div>
<div class="content opensearchserver.ignore">This content will not be indexed in OpenSearchServer.</div>
  
{% endhighlight %} 
