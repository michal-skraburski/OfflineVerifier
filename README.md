## OfflineVerifier
PaperMC plugin designed to replace the MC login mechanism to allow offline players to securely join servers

## Instructions
Install a PaperMC server and move the latest release JAR into the plugins directory.
Start the server, enable the EULA and set the server.properties `onlineMode=false whitelist=true`,
`whitelist` is optional however recommended.
Restart and use the console to add yourself to the whitelist if enabled.
Login with a offline or online account and register the account, then login.

## Commands
`/register <password> <password>`
`/login <password>`
The following commands allow you to skip the login requirement if the connecting player's IP is the same as when the command was run.
`/trustip`
`/distrustip`
