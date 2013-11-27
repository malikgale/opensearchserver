---
layout: post
title:  "How to sort on a specific field and then on relevance"
date:   2013-11-22 15:48:52
categories: faq querying
---

Documents can easily be sorted on one field, for instance price:

{% highlight json %}
{
  "query" : "phone",
  "start" : 0,
  "rows"  : 10,
  "sorts": [
    {
      "field": "price",
      "direction": "ASC"
    }
  ]
}
{% endhighlight %}

But documents with the same `price` values must then seemed to be sorted randomly.

A second sort can be added, on `score`. `score` is not a real field of the documents but it is an information that can be used at query time to sort documents.

{% highlight json %}
{
  "query" : "phone",
  "start" : 0,
  "rows"  : 10,
  "sorts": [
    {
      "field": "price",
      "direction": "ASC"
    },
    {
      "field": "sort",
      "direction": "DESC"
    }
  ]
}
{% endhighlight %}