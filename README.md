# android-barcode-scanner
A simple application to scan 1D/2D barcodes in android using ZXing library 

Details of the entire Application Available on my Blog
https://rajislearning.com/android-barcode-scanner-vertical-orientation-and-camera-flash/

Built using Android Studio with gradle

- Update the build.gradle file and add the dependency
- Add camera permission in AndroidManifest.xml
- This app only supports min sdk -9
- Vertical Scan - 
	- create new activity that extend CaptureActivity
	- update androidmanifest.xml and add the orientation as fullSensor
