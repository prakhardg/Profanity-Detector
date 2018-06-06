To include into your project.
get JAR from : dist->ProfanityDetector.jar and include into your project libraries.

import toppr.ProfaneDictionaryService
ProfaneDictionaryService.getInstance().isProfane('String') returns boolean true if the sentence contains profanity.
Otherwise returns false.