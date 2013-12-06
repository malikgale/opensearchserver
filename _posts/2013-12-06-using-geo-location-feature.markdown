---
layout: post
title:  "Using geolocation queries"
date:   2013-12-06 10:00:00
categories: faq querying
---

OpenSearchServer supports Geolocation requests. By embedding the coordinates (longitude and latitude) in the indexed documents, it is possible to filter a result on the documents which are in a defined rectangle.

## Index your document with Geolocation information

### Prepare the schema

Before being able to apply geospatial queries, you have to provide the latitude and the longitude of the document.

The schema should contains two fields which will hold these informations:

- latitude (indexed) 
- longitude (indexed)

Choose the analyzer corresponding to the [coordinate system](http://en.wikipedia.org/wiki/Geographic_coordinate_system):

- GeoRadianAnalyzer
- GeoDegreesAnalyzer

<img class="img-rounded" src="{{ site.baseurl}}/assets/faq/geo_fields.png" />

### Index the data

The coordinate (latitude and longitude) are supposed to be expressed in decimal format:

- For Degrees: -52.090904
- For Radian: -0.675757575575

#### Using the API, just add the two fields on your JSON document

The [JSON indexing API](https://github.com/jaeksoft/opensearchserver/wiki/Document-put-JSON) is documented on our [API wiki](https://github.com/jaeksoft/opensearchserver/wiki/)


{% highlight json %}
[
  {
    "lang": "ENGLISH",
    "fields": [
      { "name": "city", "value": "New-York" },
      { "name": "latitude", "value": 40.7142700 },
      { "name": "longitude", "value": -74.0059700 }
    ]
  },
  {
    "lang": "FRENCH",
    "fields": [
      { "name": "city", "value": "Paris" },
      { "name": "latitude", "value": 48.8534100 },
      { "name": "longitude", "value": 2.3488000 }
     ]
   },
   {
    "lang": "GERMAN",
    "fields": [
      { "name": "city", "value": "Berlin" },
      { "name": "latitude", "value": 52.5243700 },
      { "name": "longitude", "value": 13.4105300 }
     ]
   }
] 
{% endhighlight %}  