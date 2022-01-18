
# SnapTabLayout [![Tweet](https://img.shields.io/twitter/url/http/shields.io.svg?style=social)](https://twitter.com/intent/tweet?text=Android%20library%20for%20fluid%20tablayout%20animation&url=https://github.com/nirukk52/SnapTabLayout&hashtags=android,design,animation,androiddev,developers)
### Show some :heart: and star the repo to support the project
<!--[![CircleCI](https://circleci.com/gh/nirukk52/SnapTabLayout/tree/master.svg?style=shield)](https://circleci.com/gh/nirukk52/SnapTabLayout/tree/master) 
[ ![Download](https://api.bintray.com/packages/nirukk52/maven/SnapTablayout/images/download.svg) ](https://bintray.com/nirukk52/maven/SnapTablayout/_latestVersion) -->
[![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-SnapTabLayout-blue.svg?style=flat )]( https://android-arsenal.com/details/1/7243 ) 
[![Material Up](https://img.shields.io/badge/materialup-SnapTablayout-blue.svg)](https://www.uplabs.com/posts/snaptablayout) 
[![Android_Weekly](https://img.shields.io/badge/androidweekly-%23332-blue.svg)](http://androidweekly.net/issues/issue-332)


This library is the implementation of TabLayout as seen on popular messaging app Snapchat <a href="https://www.snapchat.com/download" target="_blank">Snapchat.com</a>.

It can be used to animate Three or Five tabs.

## 👏 Demo
![snaptablayout demo 1](https://user-images.githubusercontent.com/28961063/46969922-3c1dd680-d0d5-11e8-81b6-60cf032dcb92.gif)  ![snaptablayout demo 2](https://user-images.githubusercontent.com/28961063/47112776-f26bf200-d274-11e8-9475-879e402a1aa9.gif)   

## Contents
 - [Installation](#💻-Installation)
 - [How to use / Sample](#❔-Usage)
 	- [Customization](#📐-Customization)
 - [Bugs and feedback](#bugs-and-feedback)

## 💻 Installation

     implementation 'com.fridayof1995.tabanimation:SnapTablayout:0.0.7'

## ❔ Usage
### Step 1
```xml    
<com.fridayof1995.tabanimation.SnapTabLayout
        android:id="@+id/tabLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
	.
	.				     
	custom:numOfTabs="three"
        />
```
### Step 2
* ### Tab Number (Required)
This parameter specifies the number of tabs required:
```
setNumOfTabs(SnapTabLayout.NumOfTabs.THREE);
or
setNumOfTabs(SnapTabLayout.NumOfTabs.FIVE);
```

* ### Setting the icons (Required)
This parameter specifies the icons to be inflated:
All of the below are ImageButtons so you can set image, background etc.
![tab_name_explain](https://user-images.githubusercontent.com/28961063/47033253-f4a95000-d191-11e8-91b6-c95fce21b439.png)
```
tabLayout.smallCenterButton.setImageResource()
tabLayout.largeCenterButton.setImageResource()
tabLayout.startButton.setImageResource()
tabLayout.endButton.setImageResource()

//Below required only when using five tabs.
tabLayout.midStart.setImageResource()
tabLayout.midEnd.setImageResource()
```

## 📐 Customization

* ### Background (Optional)
This parameter sets the background in extended and collapsed tab mode:
```
tabLayout.setBackgroundCollapsed(R.drawable.tab_gradient_collapsed) // By default black fall gradient.
tabLayout.setBackgroundExpanded(R.drawable.tab_gradient_expanded)
```

* ### Color Transition in Icons (Optional)
This parameter sets the *ColorFilter* in extended and collapsed tab mode:
```
// When the layout moves from expanded to collapsed: Icons color transitions from white to black.
tabLayout.setTransitionIconColors(R.color.white, R.color.black)

```

* ### Color Transition in ViewPager Background (Optional)
This parameter gives a smooth color transition to the background of viewpager as seen in demo:
```
tabLayout.setVpTransitionBgColors(LeftSideColor: android.R.color.holo_purple
                , CenterColor: android.R.color.black
                , RightSideColor: android.R.color.holo_orange_dark)

```
## Bugs and Feedback

For bugs, feature requests, and discussion please use [GitHub Issues](https://github.com/nirukk52/SnapTabLayout/issues).

## 👨 Developed By

```
Niranjan Kurambhatti
```

## 📃 License

    Copyright 2018 Niranjan Kurambhatti

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
