package com.rss.diseasetracker.service;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.rss.diseasetracker.model.LocationWiseStats;

@Service
public class DiseaseDataTrackerService {
	private static String DISEASE_TRACKER_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";
	private List<LocationWiseStats> currentStatList = new ArrayList<LocationWiseStats> ();
	
	public List<LocationWiseStats> getCurrentStatList() {
		return currentStatList;
	}

	@PostConstruct
	@Scheduled(cron = "* * 1 * * *")
	public void getDiseaseData() throws IOException, InterruptedException {
		HttpClient httpClient = HttpClient.newHttpClient();
		
		HttpRequest request = HttpRequest.newBuilder()
								.uri(URI.create(DISEASE_TRACKER_DATA_URL))
								.build();
		HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		
		StringReader csvBodyReader = new StringReader(httpResponse.body());
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
		List<LocationWiseStats> statList = new ArrayList<LocationWiseStats> ();
		LocationWiseStats locationWiseStats = null;
		int counter = 0;
		for (CSVRecord record : records) {
			if(record != null && record.size() > 0) {
				locationWiseStats = new LocationWiseStats();
				
				locationWiseStats.setCountry(record.get("Country/Region"));
				locationWiseStats.setState(record.get("Province/State"));
				try {
					System.out.println("record-:"+record);
					locationWiseStats.setCurrentTotalCases(Integer.parseInt(record.get(record.size() - 1)));
					locationWiseStats.setDiffernceSinceYesterday((Integer.parseInt(record.get(record.size() - 1)))-(Integer.parseInt(record.get(record.size() - 2))));
				}catch(NumberFormatException nfe) {
					System.out.println(counter+"record-MESSAGE:"+record+"----"+nfe.getMessage());
				}catch(Exception e) {
					System.out.println(counter+"MESSAGE:"+record+"----"+e.getMessage());
				}
				statList.add(locationWiseStats);
			}++counter;
		}
		
		this.currentStatList = statList;
	}
}
