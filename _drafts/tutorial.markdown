---
layout: post
title:  "Crawler un site internet et obtenir un moteur de recherche"
date:   2013-12-04 10:00:00
categories: tutorial
---

# Tutoriel en cours d'écriture

Ce tutoriel est actuellement en cours de rédaction et n'est pas encore finalisé.


#  Crawler un site internet et obtenir un moteur de recherche

Ce tutoriel simple permet de prendre rapidement en main les principales fonctionnalités d'[OpenSearchServer](http://www.open-search-server.com/fr).

Vous apprendrez à :

* **crawler un site web**,
* **construire l'index de recherche**, 
* mettre en place **une page de recherche** paginée, 
* **configurer des facettes**, 
* paramétrer la mise en avant d'extraits de résultats, 
* **activer l'auto-complétion**.

Voici la page de recherche finale que nous obtiendrons :
![Résultat final](http://alexandre-toyer.fr/nonSitePerso/oss/tuto_resultat2.PNG)

Pour illustrer notre tutoriel, nous avons pris l’exemple d’un site d'actualités. Nous avons créé pour cela 4 pages fictives :

* [http://www.open-search-server.com/fr/site-de-test-crawler/](http://www.open-search-server.com/fr/site-de-test-crawler/)
  * [http://www.open-search-server.com/fr/site-de-test-crawler-le-chomage-est-en-baisse/](http://www.open-search-server.com/fr/site-de-test-crawler-le-chomage-est-en-baisse/)
  * [http://www.open-search-server.com/fr/site-de-test-crawler-la-coupe-du-monde-2040](http://www.open-search-server.com/fr/site-de-test-crawler-la-coupe-du-monde-2040/)
  * [http://www.open-search-server.com/fr/site-de-test-crawler-la-ceremonie-des-oscars/](http://www.open-search-server.com/fr/site-de-test-crawler-la-ceremonie-des-oscars/)

Pour commencer il vous suffit d'[Installer OpenSearchServer en 3 minutes](http://www.open-search-server.com/fr/tester-opensearchserver).

## Quelques définitions

Avant de démarrer ce tutoriel il est important de connaitre les principaux constituants d'un moteur de recherche :

* **Index de recherche** : l'index est l'endroit où les documents sont enregistrés, découpés et classés selon différents algorithmes afin de permettre ensuite leur recherche rapide.
* **Crawler** : le crawler web explore les sites web demandés pour indexer leurs pages. Il est capable de suivre seul les liens présents dans les pages tout en se cantonnant à un domaine précis. Il permet d'enregistrer différents types de documents : les pages web, les images, les fichiers liés, etc. Il existe aussi des crawlers de système de fichier et des crawler de base de données.
* **Schema** : le schéma détermine la structure d'un index. C'est lui qui indique quels sont les champs des documents indexés.
* **Query** : les query représentent les requêtes de recherche personnalisées. Dans une requête il est possible de configurer les champs du schéma dans lesquels rechercher, la pertinence de chaque champ, la présence de facettes et de snippets, etc.
* **Facette** : les facettes sont des compteurs de documents basés sur des valeurs partagées par tous.
* **Snippet** : les snippets sont des extraits de document permettant de mettre en avant les mots recherchés.
* **Renderer** : dans OpenSearchServer les renderers sont des pages de recherche simple à mettre en place et pouvant être proposées aux internautes
* **Parser** : les parsers servent à extraire des informations structurées à partir des documents indexés (titre, auteur, description, ...)
* **Analyzer** : les analyzers sont des composants personnalisables permettant d'appliquer certains traitements sur les textes indexés ou recherchés (découpage en token, retrait des accents, conversion d'unités, ...)
* **Scheduler** : le scheduler d'OpenSearchServer est un gestionnaire de tâches offrant la possibilité de programmer l'exécution de différents jobs 

Le schéma suivant présente les principales briques d'OpenSearchServer de manière simplifiée :

![Résultat final](http://alexandre-toyer.fr/nonSitePerso/oss/schema3.PNG)

Maintenant que tout est clair, débutons !

## Mettre en place le crawl et indexer les contenus

### Création et configuration initiale de l'index

Commençons par créer un `index`. L'index est le cœur d'OpenSearchServer, c'est autour des index que le reste des fonctionnalités s'organisent. L'index permet de stocker et d'indexer tous les **documents** qui lui sont soumis. La plupart du temps un document correspond à une page web, représentée par son URL, mais cela peut aussi être un fichier ou un contenu issu d'une base de données.

* Nom de l'index : `site`
* Template : `Empty index`

Cliquez sur `Create`.

![Création de l'index](http://alexandre-toyer.fr/nonSitePerso/oss/tuto_creation.PNG)

> OpenSearchServer propose également 2 templates d'index pré-configurés : l'un pour du crawl de site web, l'autre pour du crawl de file system. 

> Le template `Web crawler` permet de mettre en place extrêmement rapidement un système de crawl et de recherche sur son site internet en minimisant très fortement la configuration nécessaire. Cet index propose en effet un schéma très complet utilisant différents parsers et analyzer, une `query` performante et un `renderer` prêt à l'emploi.

> Nous n'emploierons cependant pas ce template pour ce tutoriel car notre but ici est justement de vous faire prendre en main les options de configuration les plus classiques qui vous permettront ensuite d'adapter le moteur à vos besoins spécifique.

L'index est créé immédiatement. Le contexte global de l'interface change et de nouveaux onglets apparaissent en haut de page.

![Onglets de la navigation principale](http://alexandre-toyer.fr/nonSitePerso/oss/tuto_tabs.PNG)

Sélectionnez l'onglet `Schema`. Le schéma permet de définir quels sont les champs de l'index. 
Un champ de schéma possède 5 propriétés :

* **Name** : le nom du champ
* **Indexed** : indique si la valeur du champ doit être indexée, ce qui permettra alors d'effectuer des requêtes dessus. Il arrive que certains champs ne soient pas utilisés dans les recherches mais doivent tout de même être retournés (voir propriété suivante) lors d'une requête de recherche.
* **Stored** : indique si la valeur du champ doit être stockée telle quelle. Cela permettra de renvoyer la donnée brute lors d'une requête de recherche.
* **TermVector** : indique si des `snippets` pourront être configurés sur ce champ. Les snippet sont des extraits de texte contenant les mots recherchés.
* **Analyzer** : les `analyzers` sont des ensembles de filtre et de traitements automatiques qui peuvent être effectués sur les valeurs indexées. 


Nous allons créer 4 champs pour indexer nos actualités : url, title, category et content.

Créez le champ url :

* **Name** : url
* **Indexed** : yes
* **Stored** : yes
* **TermVector** : no
* **Analyzer** : laisser vide

Cliquez sur le bouton `Add`.

![Création du champ url](http://alexandre-toyer.fr/nonSitePerso/oss/tuto_create_field_url.PNG)

Le champ est ajouté en dessous dans la zone __List of existing fields and their settings__.

Créez les 3 autres champs en choisissant ces  options :

* title :
  * **Name** : title
  * **Indexed** : yes
  * **Stored** : yes
  * **TermVector** : positions_offset
  * **Analyzer** : StandardAnalyzer
* category : 
  * **Name** : category
  * **Indexed** : yes
  * **Stored** : no
  * **TermVector** : no
  * **Analyzer** : laisser vide
* content :
  * **Name** : content
  * **Indexed** : yes
  * **Stored** : yes
  * **TermVector** : positions_offset
  * **Analyzer** : StandardAnalyzer

Nos 4 champs sont maintenant créés et visibles dans la liste des champs.

![Configuration du schéma](http://alexandre-toyer.fr/nonSitePerso/oss/tuto_schema_fields.PNG)

Nous devons configurer un champ par défaut et un champ unique. Pour cela sélectionnez `content` dans la première liste et `url` dans la seconde.

![Configuration du schéma](http://alexandre-toyer.fr/nonSitePerso/oss/tuto_schema_default.PNG)

#### Configuration du parser HTML

Il nous faut maintenant expliquer au moteur comment extraire certaines informations des pages crawlées afin de les ranger des les champs du schéma.

Toujours au sein de l'onglet `Schema` cliquez sur l'onglet `Parser list`. Cette page présente les différents `parser` disponibles. Beaucoup de parser sont créés par défaut. Cliquez sur le bouton `Delete` pour tous les parsers excepté pour le `HTML parser`.
Cliquez sur le bouton `Edit` sur la ligne `HTML parser`. La page d'édition du parser HTML s'affiche. Cliquez sur l'onglet `Field mapping`. Ici encore plusieurs correspondances sont pré-configurées. Dans le cadre du tutorial nous allons toutes les supprimer pour en recréer 3. Cliquez sur la petite croix rouge présente en fin de chaque ligne.

Nous allons faire en sorte que le moteur repère dans le HTML source de chaque page le titre, la rubrique et le contenu qui nous intéresse afin d'indexer ces informations proprement. Pour cela nous devons lui indiquer à partir de quel champ source travailler, dans quel champ de l'index écrire les données et via quelle expression régulière extraire ces données. Au sein de l'expression régulière c'est le groupe de capture, déterminé par des parenthèses, qui donnera sa valeur au champ.

Nous n'entrerons pas ici dans le détail de l'écriture des expressions régulières mais vous pourrez trouvez beaucoup d'informations sur le web, notamment sur ce site : http://www.regular-expressions.info/

Configurez 3 `mapping`:

* Premier mapping : nous extrayons le titre à partir du `h1` de la page
  * première liste : sélectionnez `htmlSource`
  * seconde liste : sélectionnez `title`
  * champ `captured by (reg.exp.)` : saisissez cette expression régulière : `<h1 class="post-title">(.*?)</h1>`. Cela nous permet de capturer la valeur se trouvant au sein de la balise h1
  * cliquez sur le bouton `Add`
* Second mapping : nous extrayons la rubrique à partir d'un élément du fil d'ariane 
  * première liste : `htmlSource`
  * seconde liste : `category`
  * regexp : `(?s)<a[^<]*class="rubrique"[^<]*>(.*?)</a>`
  * cliquez sur le bouton `Add`
* Troisième mapping : nous extrayons le contenu principal de l'article 
  * première liste : `htmlSource`
  * seconde liste : `content`
  * regexp : `(?s)<div class="post-entry">.*<p>&nbsp;</p>[^<]*<p>(.*?)</div>`
  * cliquez sur le bouton `Add`

Cliquez maintenant sur le bouton `Save` en bas de page.


![Configuration du parser HTML](http://alexandre-toyer.fr/nonSitePerso/oss/tuto_parser_mapping.PNG)

Voilà, le parser HTML est configuré ! Dorénavant chaque page crawlée sera traitée par ce parser avant d'être indexée. 

### Configuration du crawl

Nous devons maintenant configurer le crawler web d'OSS afin qu'il parcoure et qu'il indexe les pages désirées.

Rendez-vous dans l'onglet `Crawler` de l'index site. La section crawler contient deux sous navigation par onglets. Le premier des ces deux niveaux permet de choisir entre la configuration du crawler web, du crawler de base de données et du crawler de système de fichier.

Restons sur l'onglet `Web`. Le second niveau de navigation permet de naviguer à travers les rubriques du crawler web.

L'onglet sélectionné par défaut, `Pattern list`, est celui qui nous intéresse ici.

![Onglet par défaut du crawler web](http://alexandre-toyer.fr/nonSitePerso/oss/tuto_crawler.PNG)

Le site que nous souhaitons crawler est [http://www.open-search-server.com/fr/site-de-test-crawler/](http://www.open-search-server.com/fr/site-de-test-crawler/). Nous pouvons voir que cette URL contient les liens vers toutes les pages d'actualités. Nous pouvons donc indiquer au crawler de commencer son crawl ici et d'indexer toutes les pages se trouvant "sous" cette URL.

Dans le champ de saisie de l'onglet Pattern list indiquez `http://www.open-search-server.com/fr/site-de-test-crawler/` et `http://www.open-search-server.com/fr/site-de-test-crawler-*` puis cliquer sur le bouton Add. L'URL renseigné s'ajoute à la zone du dessous contenant toutes les URL à crawler.

La partie `-*` indique ici au crawler de parcourir toutes les pages dont l'URL débute par `http://www.open-search-server.com/fr/site-de-test-crawler-`.

Rendez-vous ensuite dans l'onglet `Field mapping`. Nous allons ici configurer le crawler pour qu'il place automatiquement l'URL de la page crawlée dans le champ `url` du schéma. Le crawler peut en effet manipuler directement un certain nombre d'éléments issus de la page web crawlée, comme par exemple son url, les headers de réponses, l'url referer, etc.

Choisissez `url` dans les deux listes déroulantes puis cliquez sur `Add`. Le mapping entre les deux champs s'ajoute immédiatement dans la zone du dessous.

![Mapping du champ url](http://alexandre-toyer.fr/nonSitePerso/oss/tuto_crawler_urlmapping.PNG)

Voilà, nous avons configuré le crawler en 2 étapes !

### Démarrage du crawl

Il est maintenant temps de démarrer le crawler. Rendez-vous pour cela dans l'onglet `Crawler` puis `Crawl process`. Différents paramètres liés au crawl peuvent être réglés ici. Saisissez 7 dans le champ `Delay between each successive access, in seconds:`, 5 dans le champ `Fetch interval between re-fetches:` et sélectionnez `minutes` dans la liste déroulante. 

Dans le bloc `Current status` choisissez `Run forever` dans la liste puis cliquez sur le bouton `Not running - click to run` afin de lancer le crawl. Ici encore, le process s'actualise immédiatement dans la zone du dessous.

Pendant que les pages sont crawlés et les documents ajoutés à l'index nous allons voir comment les recherches peuvent être effectuées dans ces documents.

> L'onglet `Manual crawl` vous permet d'observer immédiatement le comportement du crawler pour une URL précise : statut du crawl, champs parsés, etc.

## Rechercher les contenus indexés et personnaliser la pertinence des résultats

### Création de la requête de recherche full-text

Cliquez sur l'onglet `Query`. Dans le champ `Query name` saisissez `search_articles` puis cliquez sur le bouton `New query...`

![Création d'une requête de recherche](http://alexandre-toyer.fr/nonSitePerso/oss/tuto_query_search.PNG)

Les `query` peuvent ensuite être construites avec un formalisme puissant, mais facilement abordable. Il faut en effet indiquer au moteur de recherche dans quel champ la recherche full-text doit s'effectuer et quel poids accorder à chaque champ. 

En effet comme nous l'avons vu au début de ce tutorial certains champs peuvent n'être que stockés mais pas indexés, car nous ne souhaitons pas effectuer de recherche dessus.
De plus nous pouvons considérer que des documents qui contiendront les mots recherchés dans leur titre ont plus de poids, et donc plus de pertinence, que les documents ne contenant ces mots que dans leur contenu.

Nous pouvons donc utiliser cette requête :

    title:($$)^10 OR title:("$$")^10 OR
    category:($$)^7 OR category:("$$")^7 OR
    content:($$)^4 OR content:("$$")^4

`$$` représente ici le ou les mots saisis lors de la recherche.

Nous indiquons au moteur de rechercher d'abord dans le titre avec un poids important, puis ensuite dans la rubrique avec un poids plus faible et enfin dans le contenu de l'article.

Nous utilisons également la notation `($$)` et `("$$")` qui permet d'obtenir des documents contenant les mots recherchés soit de manière éclatée soit de manière regroupée.


Saisissez la requête dans le champ `Pattern query` puis cliquez sur le bouton `Save` se trouvant en haut à droite de la page.

Nous pouvons à présent effectuer des recherches sur les documents qui ont été indexés durant le temps de création de la requête.

Cliquez sur le bouton `Edit` de la requête afin de revenir à sa page d'édition. Dans le champ `Enter the query` saisissez par exemple `coupe` puis cliquez sur le bouton `Search`. Le moteur retourne les documents correspondant dans la zone du dessous. 

> Vous pouvez laisser le champ `Enter the query` vide ou saisir le mot clé `*:*` pour obtenir tous les documents.

## Proposer une page de recherche aux utilisateurs

Jusqu'à présent nous avons pu rapidement mettre en place un index de document, un crawler de page web et une manière de rechercher les documents.

Nous allons maintenant voir comment mettre à disposition des utilisateurs de notre site internet ce moteur de recherche.

Cliquez sur l'onglet `Renderer` puis sur le bouton `New renderer...`.

Dans le champ `Renderer name` saisissez `default` et sélectionner la requête `search_articles` dans la liste déroulante `Request name`.

Cliquez sur l'onglet `Fields`, choisissez `title` dans la seconde liste déroulante et `url` dans la liste `URL field`. Cliquez sur le bouton `+` situé en fin de ligne.
 
Cliquer ensuite sur le bouton `Create`.

![Le renderer](http://alexandre-toyer.fr/nonSitePerso/oss/tuto_renderer.PNG)

Nous avons ainsi créer une page de recherche qui utilisera la requête `search_articles` configurée au préalable. 

Dans la liste des `renderer` cliquez sur le bouton `View` sur la ligne du renderer `default`. 

La page obtenue contient un formulaire de recherche directement utilisable par les internautes ! 

Vous pouvez retourner dans l'édition du renderer pour définir des CSS personnalisés et modifier les autres paramètres. 
Saisissez par exemple les règles CSS suivantes pour modifier l'affichage de la page de recherche :

    * { font-family: verdana, arial; font-size:12px;}
    .ossfieldrdr1 { margin:10px 0 0 0; padding:10px 0 0 0; border-top:1px solid #ebebeb}
    #oss-facet ul li:first-child{font-weight:bold; padding:3px; background:#efefef; margin:0 0 5px 0; width:140px;}
    .oss-input-div { padding:10px 0 0 0;}

> L'onglet `Testing` vous fournira le code source de l'iFrame à intégrer sur une page de votre site internet.

## Ajouter une facette

Nous avons configuré le crawler et le schéma pour qu'à chaque article indexé soit associé une rubrique. Nous allons maintenant voir comment exposer cette rubrique en tant que facette. 

Les facettes sont des compteurs thématiques de résultats, qui servent également de filtre de recherche.

Cliquez sur l'onglet `Query` puis sur le bouton `Edit` de la requête `search_articles`.

Cliquez ensuite sur l'onglet `Faceted fields`, choisissez `category` dans la liste puis cliquez sur le bouton `add facet`. Cliquez ensuite sur le bouton `Save` en haut à droite.

Ré-affichez la page du renderer (clic sur `View` dans l'onglet `renderer`) et effectuez une recherche : la facette est automatiquement ajoutée !

![Facettes](http://alexandre-toyer.fr/nonSitePerso/oss/tuto_facets.PNG)

> Comme vous avez dû vous en apercevoir depuis le début du tutoriel OpenSearchServer prend en compte _à chaud_ tous les changements de configuration ! Aucun redémarrage de service n'est nécessaire.

## Ajouter des snippets

Les snippets sont des extraits de résultats contenant le ou les mots recherchés. Nous allons configurer une snippet sur le titre du document et une autre sur le champ content.

Cliquez sur l'onglet `Query` puis sur le bouton `Edit` de la requête `search_articles`.

Cliquez ensuite sur l'onglet `Snippet fields`, choisissez `title` dans la liste puis cliquez sur le bouton `add snippet`. Dans le champ `Tag` saisissez par exemple `strong`. 

Répétez l'opération pour le champ `content`.

![Utilisation des snippets dans le renderer](http://alexandre-toyer.fr/nonSitePerso/oss/tuto_renderer_fields.PNG)

Cliquez ensuite sur le bouton `Save` en haut à droite.

Nous devons maintenant indiquer au renderer d'utiliser les snippets à la place du champ `title` configuré plus tôt. Cliquez sur l'onglet `Renderer` puis sur le bouton `Edit`. Dans l'onglet `Fields` supprimer le champ `title` préalablement configuré.
Choisissez `SNIPPET` dans la première liste déroulante puis `title` dans la seconde. Dans `URL Field` choisissez `url` puis cliquez sur le bouton `+` en fin de ligne.
Répétez l'opération pour la snippet content, sans configurer d'url.

Cliquez sur le bouton `save` en bas de page et ré-affichez le renderer puis effectuez une recherche. Le ou les mots recherchés sont à présent affichés en gras dans la page de résultat.

![Facettes](http://alexandre-toyer.fr/nonSitePerso/oss/tuto_snippet.PNG)

## Ajouter l’autocomplétion

OpenSearchServer gère très simplement l'ajout de l'auto-complétion. Pour ajouter cette fonctionnalité nous devons configurer un nouveau champ dans le schéma et compléter le parser HTML pour envoyer des données dans ce champ. OpenSearchServer s'occupe ensuite seul de construire l'index d'auto-complétion et de renvoyer les résultats au fur et à mesure de la frappe.
Pour l'exemple nous allons faire en sorte que l'auto-complétion fonctionne sur les titres de pages.

Cliquez sur l'onglet `Schema`. Dans l'onglet `Fields` ajoutez un nouveau champ :
* **Name** : autocomplete
* **Indexed** : yes
* **Stored** : yes
* **TermVector** : no
* **Analyzer** : AutoCompletionAnalyzer

Cliquez sur le bouton `Add`.

Cliquez sur l'onglet `Parser list` puis éditez le parser `HTML parser`.  Ajoutez une correspondance entre la valeur `title` (première liste) et le champ `autocomplete` (seconde liste). Cliquez sur `Add` puis sur `Save`.

![Configuration de l'auto-complétion](http://alexandre-toyer.fr/nonSitePerso/oss/tuto_parser_autocomplete.PNG)

Il faut maintenant attendre 1 à 2 minutes pour que le crawler passe à nouveau sur les pages pour ajouter des données dans le nouveau champ auto-complete. Assurez-vous pour cela que le crawler soit encore en train de tourner, autrement relancez-le (onglet `Crawler` / `Web crawler` / `Crawl process`).

Passez ensuite sur l'onglet `Schema` / `Auto-completion`. Dans la liste `Field source` choisissez le champ autocomplete puis cliquez sur le bouton `Build`. La valeur indiquée en face de `Number of terms` doit passer de 0 à un nombre plus important. Cliquez sur `save`.

![Configuration de l'auto-complétion](http://alexandre-toyer.fr/nonSitePerso/oss/tuto_autocomplete_config.PNG)

> Le processus de reconstruction de l'index d'auto-complétion peut facilement être exécuté de manière régulière via la création d'un `job` dans le puissant gestionnaire de tâche d'OpenSearchServer (onglet `Scheduler`).

Ré-affichez la page du renderer (clic sur `View` dans l'onglet `renderer`) et commencez à taper un mot, par exemple `cho`. L'auto-complétion s'est directement ajoutée à la page de recherche !
Les valeurs proposées sont cependant illisibles car elles ne sont pas encore mises en forme.

Retournez sur l'onglet `Renderer` puis éditer le renderer. Dans l'onglet `CSS Styles` ajoutez ces lignes :
 
    .osscmnrdr { font-family: arial,sans-serif; }
    .ossinputrdr { font-size:inherit; }
    .ossbuttonrdr { font-size:inherit; }
    #ossautocomplete { margin: 0;cursor: pointer;padding-left:3px;padding-right:3px; }
    #ossautocompletelist { background-color: #FFFFFF;text-align: left;border-left: 1px solid #D3D3D3;border-bottom: 1px solid #D3D3D3;border-right: 1px solid #D3D3D3; }
    .ossautocomplete_link { color:#222222;background-color: #FFFFFF;padding: 2px 6px 2px 6px; }
    .ossautocomplete_link_over { color:#222222;background-color: #F5F5F5;padding: 2px 6px 2px 6px; }
    .ossnumfound { padding-bottom:10px;padding-top:10px; }
    .oss-paging { text-align: center; }
    .ossfieldrdr1 { font-size:120%; }
    .ossfieldrdr3 { color: #0E774A; }

Cliquez ensuite sur le bouton `Save` en bas de page et ré-actualisez la page de recherche. 

## Que faire ensuite ?

Nous venons de mettre en pratique quelques-unes des très nombreuses fonctionnalités proposées par OpenSearchServer. 

Vous pouvez maintenant découvrir le reste de [notre Centre de documentation](http://www.open-search-server.com/confluence/display/EN/Home), qui vous permettra de comprendre les autres paramétrages du moteur.

Vous y trouverez également toute la documentation sur l'ensemble des API fournies par OpenSearchServer. L'utilisation de ces API, couplée avec [nos librairies clientes](https://github.com/jaeksoft), vous permettra d'intégrer très facilement et finement le moteur de recherche à votre application.

N'hésitez pas à créer un nouvel index en utilisant cette fois le template `Web crawler` pour découvrir des options d'indexation et de recherche encore plus puissantes !