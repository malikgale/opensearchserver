---
layout: post
title:  "How to allow search into words"
date:   2013-11-22 15:48:52
categories: faq querying
---

Let's say some indexed pages contain the word "ultraviolet" and one would like to return this document when only "ultra" or "violet" is searched.

This can be achieved by creating a new Analyzer and applying this analyzer to a new field of the schema.

Analyzer will use the Ngram filter to create lots of new words from the existings ones:
<img class="col-lg-12 img-rounded" src="{{ site.baseurl}}/assets/faq/ngram.png" />

A new field must then be created. It will take its value from the existing `content` field and will use the new `NgramAnalyzer` on this content.

<img class="img-rounded" src="{{ site.baseurl}}/assets/faq/ngram_field.png" />

Content must now be re-indexed and query must be improved to allow searching in the new `mega_content` field!
