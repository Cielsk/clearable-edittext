# clearable-edittext

[![Build Status](https://travis-ci.org/Cielsk/clearable-edittext.svg?branch=master)](https://travis-ci.org/Cielsk/clearable-edittext) [![](https://jitpack.io/v/Cielsk/clearable-edittext.svg)](https://jitpack.io/#Cielsk/clearable-edittext)

Simple custom view for clearable EditText.

Function
---
![Usage](https://raw.githubusercontent.com/Cielsk/clearable-edittext/master/asset/usage.gif)

- Add a icon in the right of EditText and its subclass:
  - AutoCompleteTextView
  - TextInputEditText
- Clear texts when the icon is touched
- Hide icon when EditText not focused
- You can define your own icon drawable

Gradle Dependency
---

Step 1. Add it in your root build.gradle at the end of repositories:
```groovy
allprojects {
  repositories {
    //... 
    
    maven { url 'https://jitpack.io' }
  }
}
```

Step 2. Add the dependency
```groovy
dependencies {
  compile 'com.github.Cielsk:clearable-edittext:0.0.5'
}
```
The latest version is shown on JitPack badge.

Usage
---
By default, the clear icon is a black material design icon called 'content/ic_clear'.

Default Clearable EditText:
```xml
<com.cielyang.android.clearableedittext.ClearableEditText
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  />
```

Screenshot of demo:
![Default Icon](https://raw.githubusercontent.com/Cielsk/clearable-edittext/master/asset/default-icon.png)

And you can define your own icon drawable by assigning drawable resource to `app:clearIconDrawable`:

Custom Clearable EditText:
```xml
<com.cielyang.android.clearableedittext.ClearableEditText
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  app:clearIconDrawable="@drawable/ic_clear_grey_500_24dp"
  app:clearIconDrawWhenFocused="false"
  />
```

Screenshot of demo:
![Custom Icon](https://raw.githubusercontent.com/Cielsk/clearable-edittext/master/asset/custom-icon.png)

You can use subclasses in the same way(just change the class name).


This library also supports some event listeners
#### OnTextClearedListener

This is called when the text is cleared

```
clearableEditText.setOnTextClearedListener(new OnTextClearedListener() {
    @Override
    public void onTextCleared() {
        // your code here
    }
});
```