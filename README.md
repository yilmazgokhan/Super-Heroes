# Super Heroes App

## Introduction
I will introduce you to how to implement Google AdMob Ads via <a href="https://github.com/Explore-In-HMS/huawei.ads.admob_mediation" target="_blank"> Huawei mediation plugin</a>.


## Project Structure

Find Super Hero App is designed with MVVM design pattern.

<table>
<tr>
<td>
<a href="https://play.google.com/store/apps/details?id=com.yilmazgokhan.superhero" target="_blank" style="float:right">
    <img src="/screenshots/gp.webp" width="250">
</a>
</td>
<td>
<a href="https://appgallery.huawei.com/app/C105522945" target="_blank" style="float:right">
    <img src="/screenshots/hg.jpg" width="250">
</a>
</td>
</tr>
</table>


## Screenshots

<table>
<tr>
<td>
<img src="/screenshots/heroes_1.png" width="300"/>

Heroes List
</td>
<td>
<img src="/screenshots/heroes_2.png" width="300"/>

Heroes List
</td>
<td>
<img src="/screenshots/hero_detail.png" width="300"/>

Hero Detail
</td>
<td>
<img src="/screenshots/hero_photo.png" width="300"/>

Hero Photo
</td>
</tr>
</table>


## About Google AdMob

Google AdMob makes it easy for developers to earn money from their mobile apps with high-quality ads. AdMob maximizes the value of every impression by combining global advertiser demand, innovative ad formats, and advanced app monetization technology.
<a href="https://admob.google.com/" target="_blank">For more detail</a>


## About Huawei Ads Kit

Ads Kit leverages the vast user base of Huawei devices and Huawei's extensive data capabilities to provide you with the Publisher Service, helping you monetize traffic. Meanwhile, it provides the advertising service for advertisers to deliver personalized campaigns or commercial ads to Huawei device users.
<a href="https://developer.huawei.com/consumer/en/hms/huawei-adskit/" target="_blank">For more detail</a>


## About Huawei-Admob Mediation Plugin

You can use the Huawei-AdMob Mediation Plugin to increase your advertising revenue by just adding the implementation lines without making any changes to the code.
<a href="https://github.com/Explore-In-HMS/huawei.ads.admob_mediation" target="_blank">For more detail</a>


## How To Build?

### Modify AndroidManifest file

Replace your AdMob app ID (<a href="https://support.google.com/admob/answer/7356431" target="_blank">identified in the AdMob UI</a>) on the app's AndroidManifest.xml file. 
To do so, add a <meta-data> tag with android:name="com.google.android.gms.ads.APPLICATION_ID". You can find your app ID in the AdMob UI.
```
<manifest>
    <application>
        <meta-data
            android:name="com.google.android.gms.ads.APPLICATION_ID"
            android:value="ca-app-pub-xxxxxxxxxxxxxxxx~yyyyyyyyyy"/>
    </application>
</manifest>
```

### Prepare mediation on AdMob platform

You can follow the steps <a href="https://github.com/Explore-In-HMS/huawei.ads.admob_mediation#how-to-start" target="_blank">here</a> to prepare mediation on the Admob platform.

### Modify the AdMob ad type ids

You need to replace the lines in the Constant file with your mediated AdMob IDs.
```
object Constants {

    object AdIds {

        const val ID_LIVE_BANNER = "ca-app-pub-xxxxxxxxxxxxxxxx/yyyyyyyyyy"

        const val ID_LIVE_INTERSTITIAL = "ca-app-pub-xxxxxxxxxxxxxxxx/yyyyyyyyyy"

        const val ID_LIVE_REWARDED = "ca-app-pub-xxxxxxxxxxxxxxxx/yyyyyyyyyy"

        const val ID_LIVE_NATIVE = "ca-app-pub-xxxxxxxxxxxxxxxx/yyyyyyyyyy"
    }
}
```

Also, you need to replace the ID in the AdView component in activity_main.xml file.
```
<RelativeLayout >
    <com.google.android.gms.ads.AdView 
        ...
        ads:adUnitId="ca-app-pub-xxxxxxxxxxxxxxxx/yyyyyyyyyy" />
</RelativeLayout>
```


## Libraries

- <a href="https://dagger.dev/hilt/" target="_blank">Dagger Hilt</a>
- <a href="https://developer.android.com/guide/navigation?gclid=EAIaIQobChMIuKTt2smz8AIViKiyCh0AwgmgEAAYASAAEgLRwvD_BwE&gclsrc=aw.ds" target="_blank">Navigation Component</a>
- <a href="https://developer.android.com/guide/navigation/navigation-pass-data" target="_blank">Safe Args</a>
- <a href="https://github.com/Blankj/AndroidUtilCode" target="_blank">Android Util</a>
- <a href="https://github.com/afollestad/material-dialogs" target="_blank">Material Dialog</a>
- <a href="https://github.com/square/retrofit" target="_blank">Retrofit-2</a>
- <a href="https://github.com/airbnb/lottie-android" target="_blank">Lottie</a>
- <a href="https://github.com/mikepenz/FastAdapter" target="_blank">FastAdapter</a>
- <a href="https://coil-kt.github.io/coil/getting_started/" target="_blank">Coil</a>
- <a href="https://github.com/bumptech/glide" target="_blank">Glide</a>
- <a href="https://github.com/Baseflow/PhotoView" target="_blank">Photo View</a>
- <a href="https://developer.huawei.com/consumer/en/hms/huawei-accountkit/" target="_blank">Huawei Ads Kit</a>
- <a href="https://admob.google.com/" target="_blank">Google AdMob</a>
