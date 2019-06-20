# GetCountryInfoJT
MainActivity asks user to enter a Country name, a hint is given "South Africa"
Once user clicks "Tell me more!" button new activity is loaded CountryInfoOutput

CountryInfoOutput displays the Country Name, Country code, capital, currencies used, lanugages, flag
-at the moment flag async task will not run (hard coded address for test)
The information is received from restcountries.eu api, using a AsyncTask FetchJSONString
If user clicks on Languages: goes to activity LanguagesInfoOutput
If user clicks on Capital: goes to activity CapitalInfoOutput

LanguagesInfoOutput uses iso code defined for the language specified and searches for other countries that speak the same language.
The information is received from restcountries.eu api, using a AsyncTask FetchJSONString

CapitalInfoOutput uses specified capital to search geocode.xyz for json output of latt long. 
- at the moment does not find the https file responseCode = 403 (hard coded address for test)

----
In future:
-Set up Languages to display individual TextViews so that user can click on individual languages (at the moment can display as string with \n but then user can't use functionality - instead outputs last in JSONObject
-Flag display, retrieval of address in similar manner to Languages
-improved method to retrieve address (determination of string usage)
-if Country output gives more than one (such as if "South" was entered) give options of countries to find out more about
