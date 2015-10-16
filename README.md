# CLOUDUNIT DOCKER CLIENT

# PROBLEM

CloudUnit uses an internal docker API. Not easy to test when Docker changes its json structure.

# GOAL

Goal of this library is :
* to separate docker concepts to CloudUnit business logics
* add units tests
* add integrations tests

# TARGET

Release a jar to include into CloudUnitManager (main project)