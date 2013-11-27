---
layout: post
title:  "How to reset forgotten username or password"
date:   2013-11-27 10:00:00
categories: faq installation
---

If you forgot your user name or password or both, do the following:

1. Close the Tomcat server by closing the cmd prompt window.
2. Browse to the OpenSearchServer directory and locate the folder data.
3. Open the data folder; locate and delete `users.xml`.
4. Restart OpenSearchServer by double-clicking on `start.bat` (on Windows) or running `start.sh` (on Unix) .


![Forgotten username or password]({{ site.baseurl }}/assets/faq/Forgot_your_user_name_and_password.png)
