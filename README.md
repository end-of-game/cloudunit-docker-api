# CLOUDUNIT DOCKER CLIENT

# Context

CloudUnit uses an internal docker API. Not easy to test when Docker changes its json structure.

# Goal

Goal of this library is :
* to separate docker concepts to CloudUnit business logics
* add units tests
* add integrations tests

# Target

Release a jar to include into CloudUnitManager (main project)

# Warning

Do not use it because just a copy from CloudUnit Manager without spring dependencies.
**Full internal rewrite in progress**