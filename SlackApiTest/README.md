# Project Details

It's a maven project which helps you to do some operation on Slack API.

## Installation

To run the project Perform 
```python
mvn clean install
```

## Usage

```python
Goto  TestNG.xml file to pass your desired parameters

 <parameter name="channelIDToCheckArchieve" value="CHP9NBC9L" />
  <parameter name="ChannelTORename" value="modified" />
  <parameter name="newChannelName" value="newtestrun" />
  <parameter name="newChannelName" value="Bishwas_Test1234" />
  <parameter name="channelNameToJoin" value="Bishwas_Test1234" />
```
And then perform 
```python
mvn test
```
