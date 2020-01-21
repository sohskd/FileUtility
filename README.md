# A FileUtility for compression and decompression

> This file utility has been tested on the following
- txt
- jar

## Utilities

### File Compression

> The file compression feature supports compressing files & folders into the specified size in MB.

#### Steps
- Step 1 
    - Clone this repo to your local machine using `https://github.com/sohskd/FileUtility.git`
    
- Step 2
    - Create a Test Input Directory where this directory will contain the files to compress (eg `C:\Des\Projects\FileUtil\TestInputs`)
    - Create a Test Output Directory where this directory will contain the files after compression (eg `C:\Des\Projects\FileUtil\TestOutputs`)
    - Create a Test Decompressed Directory where this directory will contain the files after decompression (eg `C:\Des\Projects\FileUtil\TestDecom`)
    
- Step 3
    - Put in some test files in the Test Input Directory
    - Add some dummy or real files (eg On windows machine, you can make a 40MB file with `fsutil file createnew testdummy2.txt 40000000`)
    - You may create folders with files in it
- Step 4
    - Run the following command with 3 inputs (Test Input Directory Path, Test Output Directory Path and maximum size of files in MB) \
    (eg `mvn spring-boot:run -Dspring-boot.run.arguments="C:\Des\Projects\FileUtil\TestInputs,C:\Des\Projects\FileUtil\TestOutputs,10"`)
    
- Step 5
    - The resulting file will be split into the output directory where each file has the maximum size

### File Decompression

> The file compression feature supports decompressing zip files only

#### Steps
- Step 1 
    - There should be zip files in the Test Output Directory after the file compression
    - You may unzip the zip file to view the contents where the files will be of the maximum size specified during the file compression step 
 
- Step 2
    - Remove the unzipped folder in the Test Output Directory before running the decompression command. 
    - **<u>There should only be zip files in the Test Output Directory</u>**
    - Run the following command with 2 inputs (Test Output Directory Path, Test Decompressed Directory)
    (eg `mvn spring-boot:run -Dspring-boot.run.arguments="C:\Des\Projects\FileUtil\TestOutputs,C:\Des\Projects\FileUtil\TestDecom"`)
 
 - Step 3
    - The resulting decompressed file will be in the Test Decompressed Directory

### Important Notes

- There should only be zip files in the Test Output Directory before running decompression step
- The Test Decompressed Directory (where the files will be decompressed to) should be empty before running decompression step