# jenkins-library

## Overview

Some re-usable declarative pipeline configs to be used in a number of projects

## Features

This library contains the following re-usable declaritive pipelines:

### Simple Pipeline (w/ Input)

![Simple Pipeline](https://i.imgur.com/ZaVIgzy.png)

This pipeline will execute a standard CI/CD workflow consisting of `version`, `build`, `test`, and `promote` stages.  The `test` stage is a gating stage that will wait on user input before proceeding to the `promote` stage.

To use this pipeline, populate a `Jenkinsfile` in your project with the following content:

```groovy
@Library('github.com/warlordofmars/jenkins-library') _

simplePipelineWithInput(

  // text to disply as part of input step in test stage
  'Test Deploy Look Ok?',
  
  // artifacts that should be archived
  'build/*.jar'
  
)
```

### Gradle Plugin Pipeline

![Gradle Plugin Pipeline](https://i.imgur.com/J7GlbR7.png)

This is a re-usable declaritive pipeline meant to be used on several Gradle plugin projects.  The pipeline consists of the stages:  `version`, `build`, and `promote`.

To use this pipeline, populate a `Jenkinsfile` in your project with the following content:

```groovy
@Library('github.com/warlordofmars/jenkins-library') _

gradlePluginPipeline()
```

## Author

* **John Carter** - [warlordofmars](https://github.com/warlordofmars)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details