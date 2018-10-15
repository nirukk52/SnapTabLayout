# SnapTabLayout

### Show some :heart: and star the repo to support the project

This library is the implementation of TabLayout as seen on popular messaging app Snapchat <a href="https://www.snapchat.com/download" target="_blank">Snapchat.com</a>.

It can be used to animate Three or Five tabs.

# 👏 Demo
![fabulousfilter demo 1](https://raw.githubusercontent.com/Krupen/FabulousFilter/master/newDemo1.gif)  ![fabulousfilter demo 1](https://raw.githubusercontent.com/Krupen/FabulousFilter/master/newDemo2.gif)

## Contents
 - [Installation](#installation)
 - [How to use / Sample](#Usage)
 - [Bugs and feedback](#bugs-and-feedback)

## 💻 Installation

    implementation 'com.fridayof1995.tabanimation:SnapTabLayout:0.0.1'

## ❔ Usage

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
# Parameters

* ### Tab Number (Required)
This parameter specifies the number of tabs required:
```
setNumOfTabs(SnapTabLayout.NumOfTabs.THREE);
or
setNumOfTabs(SnapTabLayout.NumOfTabs.FIVE);
```

* ### Setting the icons (Required)
This parameter specifies the icons to be inflated:
```
setCenterIcons(R.drawable.ic_ring, null); // Pass null if you want to hide the bottom center Icon.
setCornerIcons(R.drawable.ic_start_first,R.drawable.ic_end_last); // Set the start and end corner icons.

// Required only for Five tabs.
setMiddleIcons(R.drawable.ic_second,R.drawable.ic_secondlast); // Set the second and second-last icon.
```

* ### Background (Optional)
This parameter sets animation duration of translate and scale animation in `milliseconds`:
```
setBackground(R.drawable.fall_gradient); // Default black_fall gradient as seen in demo.
```

### And that's it! Enjoy!

## Bugs and Feedback

For bugs, feature requests, and discussion please use [GitHub Issues](https://github.com/leandroBorgesFerreira/LoadingButtonAndroid/issues).

## 👨 Developed By

```
Niranjan Kurambhatti
```
- Website (http://wajahatkarim.com)
- Twitter (http://twitter.com/wajahatkarim)
- Medium (http://www.medium.com/@wajahatkarim3)
- LinkedIn (http://www.linkedin.com/in/wajahatkarim)

# 👍 How to Contribute
1. Fork it
2. Create your feature branch (git checkout -b my-new-feature)
3. Commit your changes (git commit -am 'Add some feature')
4. Push to the branch (git push origin my-new-feature)
5. Create new Pull Request

# 📃 License

    Copyright 2018 Wajahat Karim

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
