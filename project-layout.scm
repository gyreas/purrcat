(project-layout
  ("LICENSE"
   "app.sh"
   "formal-all.sh"
   "Makefile"
   "pom.xml"
   "README.md"
   (src
     (aadesaed
       (cat 
         (app "App.java")
         (cmdline "Args.java")
         (input "ReadFile.java"))))
   (test
     (consistent "consistent.txt" TEST_consistent.java)
     (dummy "dummy.txt" TEST_dummy.java)
     (empty "empty.txt" TEST_empty.java)
     (fox "fox.txt" TEST_fox.java)
     (spiders "spiders.txt" TEST_spiders.java)
     (tabbed "tabbed.txt" TEST_tabbed.java)
     (thebustle "thebustle.txt" TEST_thebustle.java)
     (three "three.txt" TEST_three.java)))))





