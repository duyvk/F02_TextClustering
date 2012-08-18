package csv2ARF;
import weka.core.EuclideanDistance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.Attribute;
import weka.core.converters.ConverterUtils.DataSink;
import weka.classifiers.Classifier;
import weka.classifiers.rules.ZeroR;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.J48graft;
import weka.classifiers.trees.LADTree;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;



import au.com.bytecode.opencsv.CSVReader;

public class WekaURLClassification {

	/**
	 * @param args
	 * @throws Exception
	 */
	@SuppressWarnings("null")
	public static void main(String[] args) throws Exception {
		Classifier m_classifier = new J48graft();
		//	Classifier m_classifier = new LADTree();
		//	Classifier m_classifier = new ZeroR();

		String trainingFile = "www.interstatemusic.com_url_data_training.csv";
		String testFile = "www.interstatemusic.com_url_data_test.csv";
		Instances trainingDataset = null;
		Instances testDataset = null;
		trainingDataset = populateInstances(trainingFile);
		trainingDataset.setClassIndex(1);
		testDataset = populateInstances(testFile);
		testDataset.setClassIndex(1);
	    m_classifier.buildClassifier(trainingDataset);
	    double sum = testDataset.numInstances();
	    FileWriter writer = new FileWriter("www.interstatemusic.com_prediction_results"+".csv");
        try
        {
       	 for ( int i = 0;i<sum;i++)

       	 {
       		 System.out.println( i + " prediction: " + m_classifier.classifyInstance(testDataset.instance(i)));
       		// String str = ""+m_classifier.classifyInstance(testDataset.instance(i));
       		 String str = ""+ testDataset.attribute(1).value((int)m_classifier.classifyInstance(testDataset.instance(i)));
       		 String str2= ""+testDataset.instance(i).stringValue(0);
       		 writer.append(str2);
       		 writer.append(",");
       		 writer.append(str);
       		 writer.append('\n');

       	 }
       	 writer.flush();
       	 writer.close();
        }
        catch (IOException e) {
       	 // TODO Auto-generated catch block
       	 e.printStackTrace();
        }

	}
	private static Instances populateInstances(String filename){
		try{
			CSVReader reader = new CSVReader(new FileReader(filename));
			String[] nextLine;
			System.out.println(filename);
			String[] tmp = null;
			FastVector urls = new FastVector();
			FastVector dmn = new FastVector();
			FastVector attributes = new FastVector();

			int count = 0;
			Instances dataset = null;
			FastVector lines = new FastVector();
			while ((nextLine = reader.readNext()) != null)
			{
				lines.addElement(nextLine);
				count++;
			}
			
			for (int j = 0; j < count; j++)
			{
				if(j == 0)
				{
					for (int i=1; i < count ; i++){
						tmp = (String[]) lines.elementAt(i);
						urls.addElement(tmp[0]);
					}
					tmp = (String[]) lines.elementAt(1);
					dmn.addElement(tmp[2]);
					tmp = (String[]) lines.elementAt(j);
					FastVector types = new FastVector();
					types.addElement("FALSE"); 
					types.addElement("TRUE");
					attributes.addElement(new Attribute("URL", urls));
					attributes.addElement(new Attribute("Type",types));
					attributes.addElement(new Attribute("Domain", dmn));
					for(int i=3; i<tmp.length;i++){
						attributes.addElement(new Attribute(tmp[i].toString()));
					}
					
					dataset = new Instances("testdata",attributes, 10000);

				}
				else
				{
					tmp = (String[]) lines.elementAt(j);
					double[] values= new double[dataset.numAttributes()];
					values[0]=dataset.attribute(0).indexOfValue(tmp[0].toString());
					values[1]=dataset.attribute(1).indexOfValue(tmp[1].toString());
					values[2]=dataset.attribute(2).indexOfValue(tmp[2].toString());
										
					for(int i=3;i<tmp.length;i++){
						values[i] = Double.parseDouble(tmp[i]);
					}
					
					Instance inst = new Instance(1.0,values);
					dataset.add(inst);
				}
				
		    }
			System.out.print(dataset.checkForAttributeType(0));
			dataset.compactify();
			return dataset;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

}

