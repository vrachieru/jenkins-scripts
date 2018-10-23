import com.cloudbees.plugins.credentials.domains.Domain
import jenkins.model.Jenkins

def CREDENTIALS_LEVEL = Domain.global()

def credentialsStore = Jenkins.instance.getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()

credentialsStore
    .getCredentials(CREDENTIALS_LEVEL)
    .each {
        credentialsStore.removeCredentials(CREDENTIALS_LEVEL, it)
    }