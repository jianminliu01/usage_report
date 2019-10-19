Design Consideration:
I treated this assignment more like a proto type project setup rather than an interview home work.

This affects my overall design for this project: extensibility over simplicity.

I choose to set this appllication as a standalone Java app since the requirement states "Report should printed to a local printer in your computer". My interpretation of the above requirement is to directly print out a paper report to your default printer. For real world project, we should ask business for clarific tion. 

Java packages:
1. reader: Classes for retrieving data from various data sources.
2. entity:  Classes corresponding to raw data; such as files, tables, etc. In our case, it is csv files
3. cleanser: Classes for validating, cleaning up, aggregating data etc
4. model: Data model classes ready for use in the presentation
5. transfer: Classes to transfer data format
6. render: Classes for generating UI from the model data. I implemented a pdf render. HTML and MS word would be other popular options
7. util: 
8. exception: Most likely, we would like to include our own system exception
9. viewer: Classes for presenting a report visually, like printer, pdf viewer software, web browser etc


Data process:
I choose to process data as pipeline steps: read -> map -> cleanup -> map -> model data
pros: cleanly separate steps and logic, maker each steps loosen coupled, easier to changes when requirement changes
cons: tranverse data multiple times and perfornmace cost. Especially for bigger data set

Inperfact data:
The requirements specially states "beware that there may be more than one record for an employee on a single date, so it is not a perfect data in a perfect world". 
I noticed that 
1. multiple daily records per employee
2. multiple monthly records per employee
We should clarify what the data repsent, how they are collected, business rules how to use them.
My implentation is to use daily record with max minutes; sum up monthly records for multiple records situation. I separated this kind of logic into separate methods for easier changes.

This is a brief note about my design consideration. Please let me know if you are interested in more details.


Run it from command line on your local:
1. git clone https://github.com/jianminliu01/usage_report
2. mvn clean install
3. cd target and execute
   java -jar target/usage_report-0.1.jar


Run it from Intellij (should be similar from Eclipse):
1. import it as a maven project
2. Right click CellPhoneUsageReportApplication and run


Hope you won't have any issue with Java printing. In the case, please see the Cell Phone Usage Report.pdf. I printed it on my local printer.

