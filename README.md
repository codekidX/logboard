# logboard
User feedback logs

![Logboard Dialog](http://i.imgur.com/565o6IP.png)

## Installation

Add this to your root build.gradle file under repositories:

    allprojects {
		repositories {
			maven { url "https://jitpack.io" }
		}
	}

Add this to your app level build.gradle as dependency:

    com.github.codekidX:logboard:1.1
    
## Implementation


**Java**

```
// after setContentView() in onCreate add this
Logboard logboard = new Logboard();
logboard,init(true, this); //shouldShowDialogOnCrash, activity

Thread.setDefaultUncaughtExceptionHandler(logboard);

```
**Kotlin**

```
val logboard = Logboard()
logboard.init(true, this)

Thread.setDefaultUncaughtExceptionHandler(logboard);
```

## Logboard Dialog

If you want the users to report error with logboard after the crash or for some other reason you can show logboard dialog using the same instance

**Java**

```
logboard.show(); //shows the dialog (but without your app icon)

```
```
logboard.setAppIcon(ContextCompat.getDrawable(getApplicationContext(), R.mipmap.ic_launcher));
logboard.setTipIconColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
logboard.show(); //shows the dialog (with your app icon and primary color set)

```
**Kotlin**

```
logboard.show() //shows the dialog (but without your app icon)

```
```
logboard.appIcon = ContextCompat.getDrawable(applicationContext, R.mipmap.ic_launcher)
logboard.tipIconColor = ContextCompat.getColor(applicationContext, R.color.colorPrimary)
logboard.show() //shows the dialog (with your app icon and primary color set)

```

## License

```
   Copyright 2017 Ashish Shekar

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
