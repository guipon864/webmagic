# Projet Génie Logiciel Partie 2

**Auteur : Guillaume Ponsdesserre**  
**Date : Avril 2025**

## Table des matières

- [Introduction](#introduction)
- [Petites Modifications](#petites-modifications)
  - [Renommer une classe, une méthode, une variable](#renommer-une-classe-une-méthode-une-variable)
  - [Créer des variables pour supprimer des nombres magiques](#créer-des-variables-pour-supprimer-des-nombres-magiques)
  - [Supprimer du code mort](#supprimer-du-code-mort)
  - [Supprimer du code déprécié](#supprimer-du-code-déprécié)
  - [Supprimer du code commenté](#supprimer-du-code-commenté)
  - [Supprimer des imports inutiles](#supprimer-des-imports-inutiles)
  - [Ajout de Javadoc](#ajout-de-javadoc)
  - [Réorganiser une classe pour que le code soit bien structuré](#réorganiser-une-classe-pour-que-le-code-soit-bien-structuré)
- [Modifications Moyennes](#modifications-moyennes)
  - [Réduire la complexité cyclomatique](#réduire-la-complexité-cyclomatique)
  - [Lever une exception plutôt que retourner un code d'erreur](#lever-une-exception-plutôt-que-retourner-un-code-derreur)
  - [Supprimer la duplication de code](#supprimer-la-duplication-de-code)
  - [Ajouter un test pertinent](#ajouter-un-test-pertinent)
  - [Corriger un test rouge ou orange](#corriger-un-test-rouge-ou-orange)
- [Grandes Modifications](#grandes-modifications)
  - [Supprimer les cycles de dépendance entre packages](#supprimer-les-cycles-de-dépendance-entre-packages)

---

## Introduction

Comme pour la première partie, je me suis intéressé au projet Webmagic.  
Je n’ai pas pu aborder toutes les modifications, soit parce qu’elles n’étaient pas présentes dans ce projet, soit parce qu’elles n’étaient pas utiles. Par exemple, je pensais pouvoir réduire le nombre de paramètres de certaines méthodes, cependant, celles-ci étaient déjà bien optimisées et les modifier aurait compliqué le code.

## Petites Modifications

### Renommer une classe, une méthode, une variable

J’ai renommé la classe `ProcessorBenchmark` en `ProcessorBenchmarkTest` ainsi que la méthode `test` en `testModelPageProcessorPerformance` afin de respecter les conventions de nommage des classes et des méthodes. Cela permet d’identifier qu’il s’agit d’une classe de test et facilite la compréhension du code.  
[Lien vers le commit de renommage de la classe](https://github.com/code4craft/webmagic/commit/a071b59ee650fd4e3a859912eaf8736c5abfc726)  
[Lien vers le commit de renommage de la méthode](https://github.com/code4craft/webmagic/commit/84c9a42d41e2db35fbc4d27d863469a21e8d3103)

### Créer des variables pour supprimer des nombres magiques

J’ai créé des constantes dans la classe `ScriptConsole`, pour identifier les différents codes de statut. Cela évite l’utilisation de nombres magiques et améliore la lisibilité.  
[Lien vers le commit](https://github.com/code4craft/webmagic/commit/583d30ea25e4a0bb9a32a0d20cdede3e1b92ce9d)

### Supprimer du code mort

J’ai supprimé des getters et setters de la classe `ExtractRule`, qui n’étaient jamais utilisés.  
[Lien vers le commit](https://github.com/code4craft/webmagic/commit/d5a5b50d0a0149039756f46de918972730f4e09e)

### Supprimer du code déprécié

J’ai supprimé la méthode `scheduler` de la classe `Spider`, qui était dépréciée mais utilisée dans de nombreuses classes, puis j’ai modifié les différentes classes concernées. Cela permet de garder le code propre et d’éviter les confusions.  
[Lien vers le commit](https://github.com/code4craft/webmagic/commit/929656936b14463105b334397eca8c4bd2dfef33)

### Supprimer du code commenté

J’ai supprimé du code commenté dans la méthode `process` de la classe `AmazonPageProcessor`, ce qui portait à confusion.  
[Lien vers le commit](https://github.com/code4craft/webmagic/commit/bcb1cf94ef950ca87f7700c49d0211494a494602)

### Supprimer des imports inutiles

J’ai supprimé des imports inutilisés dans la classe `CssSelectorTest`, pour un code plus propre.  
[Lien vers le commit](https://github.com/code4craft/webmagic/commit/f16cef8f011649bea1ae54e3970cafcf57fb1cbb)

### Ajout de Javadoc

J’ai ajouté de la documentation dans la classe `ModelPageProcessor`, pour une meilleure compréhension des différentes méthodes.  
[Lien vers le commit](https://github.com/code4craft/webmagic/commit/0964b2d8a6c70f3f54121bf7eebb7e18eacd7d13)

### Réorganiser une classe pour que le code soit bien structuré

J’ai réorganisé la classe `Spider` pour placer les méthodes publiques en début de classe, suivies des méthodes protégées, puis privées. Cela améliore la lisibilité globale.  
[Lien vers le commit](https://github.com/code4craft/webmagic/commit/6abdbb0d018d9d6e7d1e0491be9ed87891d4bb7c)

## Modifications Moyennes

### Réduire la complexité cyclomatique

J’ai réduit la complexité de la méthode `equals` dans `Site`, de 52 à 20, en regroupant toutes les conditions dans une longue expression qui retourne `true` si elles sont toutes vérifiées, et `false` sinon. Cela simplifie aussi la compréhension des comparaisons.  
[Lien vers le commit](https://github.com/code4craft/webmagic/commit/cb67874402a00944d4712899fed24f549eeca46f)

### Lever une exception plutôt que retourner un code d'erreur

La méthode `getThreadAlive` retournait 0 si l'attribut était nul. Je l’ai remplacé par une exception `IllegalStateException`. Cela permet de signaler une erreur d'état plutôt que de retourner une valeur par défaut qui pourrait être interprétée comme valide.  
[Lien vers le commit](https://github.com/code4craft/webmagic/commit/2785f9c28d9c94fb7fcf3bd18da3e0e2e45187ae)

### Supprimer la duplication de code

J’ai créé la méthode `printResultItems` dans la classe mère `FilePersistentBase`. J’ai ensuite remplacé les parties de code dupliquées des classes filles `OneFilePipeline` et `FilePipeline` par des appels à cette nouvelle méthode. Cela permet de centraliser la logique d'impression des résultats et de réduire la duplication de code.  
De plus cette méthode est appelée dans deux méthodes `process`, elle pourrait donc être utile dans d'autres classes qui ont la méthode `process`.  
[Lien vers le commit](https://github.com/code4craft/webmagic/commit/f0c1e438d0c2900ae9c5d71c558681c9bcb2e653)

### Ajouter un test pertinent

J’ai ajouté des tests pour la méthode `isDuplicate` dans `DuplicateStorageRemover` car celle-ci n'avait aucun test, ils sont à exécuter en séquence.  
[Lien vers le commit](https://github.com/code4craft/webmagic/commit/871a19376da7b67c4e93b7fd5db8e7faeeb21122)

### Corriger un test rouge ou orange

J’ai corrigé la méthode `process`, dont le comportement était incorrect, ainsi que son test `test` qui n'était pas à jour. Lors de l'exécution globale, le test était vert, mais rouge lorsqu'exécuté individuellement.  
[Lien vers le commit](https://github.com/code4craft/webmagic/commit/1090d4d21147fe22bc7074fa8522f42d6f6008ca)

## Grandes Modifications

### Supprimer les cycles de dépendance entre packages

J’ai supprimé les cycles de dépendance impliquant le package `us/codecraft/webmagic/model`, en plaçant des classes du package model dans un dossier common afin que les dépendances se rejoignent dans ce dossier et donc briser le cycle. Cela permet un code plus modulaire, maintenable, testable et conforme à une architecture claire.  
[Lien vers le commit pour les packages formatter et annotation](https://github.com/code4craft/webmagic/commit/7b78bb9fad0f34089286bec65266e4beb4bb1a0b)  
[Lien vers le commit pour les packages model, pipeline et webmagic](https://github.com/code4craft/webmagic/commit/ed81e0ba380e462fc4f19c12717a9d8206751254)  
[Lien vers le commit pour les packages model/common, utils et selector](https://github.com/code4craft/webmagic/commit/48ec9e33406de340f2a2b0fe31e99dc2d403ae1e)
