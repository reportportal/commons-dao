# Changelog

# Unreleased

### New Features
* Added `datastore.minio.bucketPrefix` and `datastore.minio.defaultBucketName` configuration properties

## 3.2.1
##### Released: XXX 2017

### Bugfixes
* reportportal/reportportal#170 - Test run breaks with unclassified error (jbehave) #170


## 3.2
##### Released: XXX 2017

### New Features
* EPMRPP-26429/EPMRPP-26263 - Added possibility to get all latest launches
* EPMRPP-26416 - Add possibility to use a 'dot' symbol in login of user

### Bug Fixes
* EPMRPP-29167 - Statistics for deleted elements with custom defect types are still present
* EPMRPP-29337 - Widgets with Latest Launches ON include statistics for launches with In Progress status
* EPMRPP-29320 - Unclassified error for Latest Launches view selected in case no results

## 3.0
##### Released: XXX 2017

### BugFixes
* EPMRPP-23564 - GET shared dashboard request does not contain 'description' parameter
* EPMRPP-24914 - Item with investigated defect type only is not included in scope of analysis
* EPMRPP-24539 - Do not take into account items with No Defect type in analysis of following launches
* EPMRPP-25408 - No Defect items are included in scope of analysis in case item has ticket


## 2.7.0
##### Released: 28 November 2016

### BugFixes
* EPMRPP-21206 - Update personal project defaults


## 2.6.1
##### Released: 30 September 2016

### New Features
* Added GitHub auth support
* Added Personal Spaces support

### BugFixes
* Fixed issue with Launches cascade delete

## 2.6.0
##### Released: 16 September 2016

### New Features
* Initial release to Public Maven Repositories
