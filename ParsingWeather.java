
/**
 * Write a description of Part2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import org.apache.commons.csv.*;
import edu.duke.*;
import java.io.*;
public class Part2 {
    public CSVRecord hottesthourinfile(CSVParser parser)
    {
        CSVRecord largestSoFar =null;
        for (CSVRecord currentRow : parser)
        {
            largestSoFar = getLargestoftwo(currentRow,largestSoFar);
        }
        return largestSoFar;
    }
    public void testLowestHumidityInFile()
    {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord csv = lowestHumidityInFile(parser);
        System.out.println("Lowest Humidity was "+csv.get("Humidity")+ " at " +csv.get("DateUTC"));
    }
    public CSVRecord lowestHumidityInFile(CSVParser parser)
    {
        CSVRecord lowerstSoFar =null;
        for (CSVRecord currentRow : parser)
        {
            lowerstSoFar = getlowestHumidity(currentRow,lowerstSoFar);
        }
        return lowerstSoFar;
    }
    
    public CSVRecord getlowestHumidity(CSVRecord currentRow, CSVRecord lowerstSoFar)
    {
        if (lowerstSoFar ==null)
            {
                lowerstSoFar = currentRow;
            }
            else
            {
                if ((!currentRow.get("Humidity").equalsIgnoreCase("N/A")) && (!lowerstSoFar.get("Humidity").equalsIgnoreCase("N/A")) )
                
               {
                double currenthumidity = Double.parseDouble(currentRow.get("Humidity"));
                double lowersthumidity = Double.parseDouble(lowerstSoFar.get("Humidity"));
                
                if (currenthumidity < lowersthumidity)
                {
                    lowerstSoFar = currentRow;
                }
               }
               if (lowerstSoFar.get("Humidity").equalsIgnoreCase("N/A"))
               {
                   lowerstSoFar = currentRow;

                }
            }
        return lowerstSoFar;
    }
    public CSVRecord getloweroftwo(CSVRecord currentRow, CSVRecord lowerstSoFar)
    {
       
        
        if (lowerstSoFar ==null)
            {
                lowerstSoFar = currentRow;
            }
            else
            {
                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                double lowerstTemp = Double.parseDouble(lowerstSoFar.get("TemperatureF"));
                
                if ((currentTemp < lowerstTemp && currentTemp != -9999 ) || (lowerstTemp == -9999))
                {
                    lowerstSoFar = currentRow;
                }
            }
        return lowerstSoFar;
    }
    public void testlowerstInday()
    {
        FileResource fr = new FileResource("weather-2012-01-01.csv");
        CSVRecord lowerst =coldestHourInFile(fr.getCSVParser());
        System.out.println("Lowerst temperature was " +lowerst.get("TemperatureF") +" at " + lowerst.get("TimeEST"));
    }
    
    public void testHottestInday()
    {
        FileResource fr = new FileResource("weather-2012-01-01.csv");
        CSVRecord largest =hottesthourinfile(fr.getCSVParser());
        System.out.println("hottest temperature was " +largest.get("TemperatureF") +" at " + largest.get("TimeEST"));
    }
    public String fileWithColdestTemperature()
    {
         CSVRecord lowerstSoFar = null;
        DirectoryResource dr = new DirectoryResource();
        String largestfilename = "";
        for (File f : dr.selectedFiles())
        {
            FileResource fr = new FileResource(f);
            CSVRecord currentRow = coldestHourInFile(fr.getCSVParser());
            lowerstSoFar = getloweroftwo(currentRow,lowerstSoFar);
            if (lowerstSoFar == currentRow)
            {
                largestfilename = f.toString();
            }
        }
        return largestfilename;
    }
    public void testFileWithColdestTemperature()
    {
        String largestfilenamewithroute =fileWithColdestTemperature();
        int Start = largestfilenamewithroute.indexOf("weather-");
        String largestfilename = largestfilenamewithroute.substring(Start);
        System.out.println("Coldest day was in file " +largestfilename);
        
        FileResource fr = new FileResource(largestfilenamewithroute);
        CSVRecord largest = coldestHourInFile(fr.getCSVParser());
        System.out.println("Coldest temperature on that day was " +largest.get("TemperatureF"));
        System.out.println("All the Temperatures on the coldest day were:");
        for (CSVRecord line : fr.getCSVParser()) 
        {
            System.out.println(line.get("DateUTC")+": "+line.get("TemperatureF")  );
    
        }
    }
    public CSVRecord coldestHourInFile(CSVParser parser)
    {
        CSVRecord lowerstSoFar =null;
        for (CSVRecord currentRow : parser)
        {
            lowerstSoFar = getloweroftwo(currentRow,lowerstSoFar);
        }
        return lowerstSoFar;
        
    }
    public CSVRecord hottestInManyDays()
    {
        CSVRecord largestSoFar = null;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles())
        {
            FileResource fr = new FileResource(f);
            CSVRecord currentRow = hottesthourinfile(fr.getCSVParser());
            largestSoFar = getLargestoftwo(currentRow,largestSoFar);
        }
        return largestSoFar;
    }
    public CSVRecord lowestHumidityInManyFiles()
    {
        CSVRecord largestSoFar = null;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles())
        {
            FileResource fr = new FileResource(f);
            CSVRecord currentRow = lowestHumidityInFile(fr.getCSVParser());
            largestSoFar = getlowestHumidity(currentRow,largestSoFar);
        }
        return largestSoFar;
    }
    public double averageTemperatureInFile(CSVParser parser)
    {
        double totoaltemp = 0.0;
        int count =0;
        for (CSVRecord currentRow : parser)
        {
            if ((!currentRow.get("TemperatureF").contains("-9999")) )
            {double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            totoaltemp = totoaltemp+currentTemp;
            count ++;}
        }
        return totoaltemp/count;
    }
    public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value)
    {
        double totoaltemp = 0.0;
        int count =0;
        for (CSVRecord currentRow : parser)
        {
            
            if ((!currentRow.get("Humidity").equalsIgnoreCase("N/A")) && (!currentRow.get("TemperatureF").contains("-9999")))
            {
                double currenthumidity = Double.parseDouble(currentRow.get("Humidity"));
                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                if (currenthumidity >= value)
                {
                    totoaltemp = totoaltemp+currentTemp;
                    count ++;
                }
            }
        }
        return totoaltemp/count;
    }
    public void testAverageTemperatureWithHighHumidityInFile()
    {
        FileResource fr = new FileResource();
        double avgtemp = averageTemperatureWithHighHumidityInFile(fr.getCSVParser(),80);
        if (avgtemp > 0)
        {
            System.out.println("Average Temp when high Humidity is "+ avgtemp);
            
        }
        else
        {
            System.out.println("No temperatures with that humidity");
        }
    }
    
    public void testAverageTemperatureInFile()
    {
        FileResource fr = new FileResource();
        double avgtemp = averageTemperatureInFile(fr.getCSVParser());
        System.out.println("Average temperature in file is "+ avgtemp);
    }
    
    public void testLowestHumidityInManyFiles()
    {
        
        CSVRecord largest =lowestHumidityInManyFiles();
        System.out.println("Lowest Humidity was " +largest.get("Humidity") +" at " + largest.get("DateUTC"));
    }
    public CSVRecord getLargestoftwo(CSVRecord currentRow, CSVRecord largestSoFar)
    {
        if (largestSoFar ==null)
            {
                largestSoFar = currentRow;
            }
            else
            {
                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                double largestTemp = Double.parseDouble(largestSoFar.get("TemperatureF"));
                if (currentTemp >largestTemp)
                {
                    largestSoFar = currentRow;
                }
            }
        return largestSoFar;
    }
    public void testHottestInmanyday()
    {
        
        CSVRecord largest =hottestInManyDays();
        System.out.println("hottest temperature was " +largest.get("TemperatureF") +" at " + largest.get("DateUTC"));
    }
}
