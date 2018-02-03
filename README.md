# ItemToken

[![Build Status](https://ci.sweepy.pw/buildStatus/icon?job=ItemToken)](https://ci.sweepy.pw/job/ItemToken/)
[![Current Release](https://github.com/sweepyoface/ItemToken/releases)](https://img.shields.io/github/release/sweepyoface/ItemToken.svg)
[![Spigot](https://img.shields.io/badge/Spigot-Project%20Page-yellow.svg)](https://www.spigotmc.org/resources/itemtoken.41843/)
[![JDK](https://img.shields.io/badge/JDK-1.8-blue.svg)](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html)
[![License](https://github.com/sweepyoface/ItemToken/blob/master/LICENSE)](https://img.shields.io/github/license/sweepyoface/ItemToken.svg)

This plugin allows you to generate a token for a stack of item, for retrieval at a later time.

# Commands
| Command | Permission | Arguments | Description
| --- | --- | --- | --- |
| `/itemtoken help` | N/A | N/A | Prints the ItemToken help. |
| `/itemtoken create` (alias: /it c) | itemtoken.create | token, item, amount | Create a token. |
| `/itemtoken get` (alias: /it g) | itemtoken.get | token | Retrieve items from a token. |

# Downloading
You can download the latest build from [Jenkins](https://ci.sweepy.pw/job/ItemToken/).
# Compiling from source
1. Install [Apache Maven](https://maven.apache.org/).
2. Clone this repository.
3. Run `mvn clean package`.
4. The compiled jar will be in the `target` directory.
