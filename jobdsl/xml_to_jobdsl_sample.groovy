def jobconfig = """
XML JOB HERE
"""
<project>
  <actions/>
  <description></description>
  <keepDependencies>false</keepDependencies>
  <properties/>
  <scm class="hudson.scm.NullSCM"/>
  <canRoam>true</canRoam>
  <disabled>false</disabled>
  <blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding>
  <blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding>
  <triggers/>
  <concurrentBuild>false</concurrentBuild>
  <builders>
    <hudson.tasks.Shell>
      <command>header(&quot;Content-Type: text/xml&quot;)
echo &quot; xml version=&apos;1.0&apos; encoding=&apos;UTF-8&apos; standalone=&apos;yes&apos; &quot;;</command>
      <configuredLocalRules/>
    </hudson.tasks.Shell>
  </builders>
  <publishers/>
  <buildWrappers/>
</project>
/*
1. Make a copy (X) of the `xml_to_jobdsl_template.groovy` template file.
    > NB: A "JobDSL .groovy file" must be named with letters and underscores.
2. Replace the `XML JOB HERE`-part with the contents of your `config.xml` file.
    > Go to the `/config.xml` endpoint in your browser, and use the "view-source"
3. Copy the contents of the `config.xml` and paste it into X.
4. Remove `<?xml version='1.1' encoding='UTF-8'?>`.
5. Escape all `\` and `$` with backslashes.
6. Give the job a name other than `replace-me-jobdsl`. NB: don't use whitespaces.
    > Congratulations! You've now converted your job to JobDSL.
*/

def jobconfignode = new XmlParser().parseText(jobconfig)

job('sreekanth-in-jobdsl') {
    configure { node ->
        // node represents <project>
        jobconfignode.each { child ->

          def name = child.name()

          def existingChild = node.get(name)
          if(existingChild){
            node.remove(existingChild)
          }

          node << child
        }
    }
}
