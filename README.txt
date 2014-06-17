Tools used:
- Git for windows (http://msysgit.github.io/):
- TortoiseGit (https://code.google.com/p/tortoisegit/)
- Eclipse android sdk package (http://developer.android.com/sdk/index.html)

Min. API 14 (Andoid 4.0)
- Support package used for better compatibility (appcombat-v7, delivered with sdk)

Project setup instructions for Android SDK Manager
- Install these packages:
-+ Android 4.0 (for compatibility pack) / 4.4.2
-+ Android Support Repository
-+ Android Support Library
-+ Google USB Driver

Project setup instructions for Eclipse
- Import directories (Android -> Existing Andoid Code Into Workspace) in this order:
	1) appcompat_v7
	2) LVMaster3000
	3) LVMaster3000TestTest

- Set up LVMaster3000 project properies
-+ Android 
	-> Project Build Target -> Android 4.4.2
	-> Library -> Add -> android-support-v7-appcompat (only listed, if imported correctly)

- Set up LVMaster3000TestTest project properies
-+ Android
	-> Same as main project
-+ Java Build Path
	-> Projects -> Add -> LVMaster3000 (only listed, if imported correctly)

