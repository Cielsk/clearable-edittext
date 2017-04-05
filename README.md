# clearable-edittext

[![Build Status](https://travis-ci.org/Cielsk/clearable-edittext.svg?branch=master)](https://travis-ci.org/Cielsk/clearable-edittext) [![](https://jitpack.io/v/Cielsk/clearable-edittext.svg)](https://jitpack.io/#Cielsk/clearable-edittext)

Simple custom view for clearable EditText.

Function
---
![Usage](https://raw.githubusercontent.com/Cielsk/clearable-edittext/master/asset/usage.gif)

- Add a icon in the right of EditText and AutoCompleteTextView
- Clear texts when the icon is touched
- You can define your own icon drawable

Gradle Dependency
---

Step 1. Add it in your root build.gradle at the end of repositories:
```groovy
allprojects {
  repositories {
    ... 
    
    maven { url 'https://jitpack.io' }
  }
}
```

Step 2. Add the dependency
```groovy
dependencies {
  compile 'com.github.Cielsk:clearable-edittext:{latest-version}'
}
```
The latest version is shown on JitPack badge.

Usage
---

Just add the view into your layout xml file:
Clearable EditText:
```xml
<com.cielyang.android.clearableedittext.ClearableEditText
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  />
```
Clearable AutoCompleteTextView:
```xml
<com.cielyang.android.clearableedittext.ClearableAutoCompleteTextView
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  />
```
Output:
![Default Icon](https://raw.githubusercontent.com/Cielsk/clearable-edittext/master/asset/default-icon.png)

And you can define your own icon drawable by `app:clearIconDrawable`:
Clearable EditText:
```xml
<com.cielyang.android.clearableedittext.ClearableEditText
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  app:clearIconDrawable="@drawable/ic_clear_grey_500_24dp"
  />
```
Clearable AutoCompleteTextView:
```xml
<com.cielyang.android.clearableedittext.ClearableAutoCompleteTextView
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  app:clearIconDrawable="@drawable/ic_clear_grey_500_24dp"
  />
```
Output:
![Custom Icon](https://raw.githubusercontent.com/Cielsk/clearable-edittext/master/asset/custom-icon.png)
