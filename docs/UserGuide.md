---
layout: page
title: User Guide
---

FitBook is a **desktop app for managing clients, optimized for use via a Command Line Interface** (CLI) while still 
having the benefits of a Graphical User Interface (GUI). If you are a personal trainer that prefers typing over mouse 
interactions, FitBook is the perfect application for you to manage your clients!

* Table of Contents
{:toc}

<hr>

## Quick Start

1. Ensure you have Java `11` or above installed.
    * Need help? Check out the [installation guide](https://docs.oracle.com/en/java/javase/11/install/overview-jdk-installation.html).

1. Download the latest version of `FitBook.jar` from [here](https://github.com/AY2324S2-CS2103T-T17-3/tp/releases).

1. Copy the file to the folder you want to use as the **home folder** for FitBook.

1. Double-click the FitBook.jar file to launch it.
    * If that doesn't work, try the following steps:

        1. Open a command prompt (**cmd** for Windows, **Terminal** for Mac)

        1. Use `cd` to navigate to the folder containing FitBook.jar
            * e.g. `cd ~/Downloads/FitBook/`

        1. Run `java -jar FitBook.jar` to launch FitBook.

1. A GUI similar to the one depicted below should appear in a few seconds. The app contains some sample data for you to familiarize yourself with the UI.<br>

![Ui](images/Ui.png)

6. Read through `help`, type any command listed into the input box and press `enter` <br>
   These are some example commands you can try!
 
   * `add n/John p/98765432` Adds a client named
   `John Doe` with the number `98765432` to FitBook.

   * `clear` : Clears all clients from the list.

   * `delete 3` : Deletes the third client shown in the list.
   
   * `exit` : Exits the app.

   * `list` : Lists all clients.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Launches the help menu depicted below. <br>
Clicking `Open User Guide` leads to the User Guide website.

![help message](images/helpMessage.png)
Format: `help`

<hr>

### Adding a client: `add`

Adds a client to the FitBook.

Format: `add n/NAME p/PHONE_NUMBER [e/EMAIL] [a/ADDRESS] [nt/NOTE] [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A client can have 0 or more tags.
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@gmail.com a/John street, block 123, #01-01 nt/john from school`
* `add n/Betsy Crowe t/friend e/betsycrowe@hotmail.com a/Newgate Prison p/1234567 nt/likes donuts t/criminal`
<hr>

### Listing all clients : `list`

Displays an indexed list of all clients in FitBook.

Format: `list`
<hr>

### Editing a client : `edit`

Edits information tagged to an existing client

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [nt/NOTE] [t/TAG]…​`

* Edits the clients at the specified `INDEX`. The index refers to the index number shown in the displayed client list. 
* The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the client will be removed i.e adding of tags is not cumulative.
* You can remove all the client’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st client to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd client to be `Betsy Crower` and clears all existing tags.
<hr>

### Adding a note to clients : `note`

Format: `note INDEX nt/NOTE`

* Edits the note of the client specified by `INDEX`. The index refers to the index number shown in the displayed client list. The index **must be a positive integer** 1, 2, 3, …​
* Existing note will be updated to the input note.

> While this can also be done using the `edit` command, this `note` command serves as a faster way for users to directly modify a note.

Examples:
*  `note 1 nt/History of asthma` - Edits the note of the 1st client to `History of asthma`.
*  `note 2 nt/Previously sprained both ankles` - Edits the note of the 2nd client to `Previously sprained both ankles`.
<hr>

### Searching clients: `find`

Finds all clients that match the specified attributes.

Format: `find [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [nt/NOTE] [t/TAG]…​`

* The search is case-insensitive. e.g `hans` will match `Hans`
* Any fields can be searched.
* Multiple fields can be search in one command
    * All fields must match (e.g `find n/Wendy p/91234567` will match with a contact whose name contains `wendy` and phone number contains `91234567`)
* All fields except `TAG` will be matched based on substring (e.g `Wen` will match `Wendy`)
<div markdown="block" class="alert alert-warning">:warning: **Take note:**
* Unlike other fields, `TAG` must be an exact match (case-insensitive)
* E.g `find t/fri` will not match the tag `friend`
* But `find t/fRieNd` will match the tag `friend`
</div>


Examples:
* `find n/Wendy` returns `Wendy Son` and `Wendy Kim`
  ![result for 'find n/Wendy'](images/FindNameMultiple.png)
* `find n/Wendy t/Lover` returns `Wendy` (`Name` contains `Wendy` and is tagged with `Lover`)
  ![result for 'find n/Wendy t/Lover'](images/FindNameTag.png)

<hr>

### Deleting a client : `delete`

Deletes the specified client from FitBook.

Format: `delete INDEX`

* Deletes the client at the specified `INDEX`.
* The index refers to the index number shown in the displayed client list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the second client in the list.
* `find Betsy` followed by `delete 1` deletes the first client in the results of the `find` command.

<hr>

### Clearing all entries : `clear`

Clears all client information from FitBook.

**NOTE: This command is irreversible and should be used with caution.**

If you are sure of your decision to clear all client information, use the `/confirm` prefix with this command to execute it.

Format: `clear /confirm`

<hr>

### Exiting the program : `exit`

Exits FitBook.

Format: `exit`
<hr>

### Saving the data

FitBook data is saved in the hard disk automatically after any command that changes the data. 
There is no need to save manually.

<hr>

### Editing the data file

FitBook data is saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. 
Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, FitBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause FitBook to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>
<hr>

### Save contact to phone

![QrCodeContactCard](images/QrCodeContactCard.png)

To save a contact to your mobile phone from FitBook, simply scan the QR code next to the contact.

<img src="images/QRScanning.png" height="480">
<img src="images/QRContact.png" height="480"> 
<hr>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install FitBook in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous FitBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER [e/EMAIL] [a/ADDRESS] [nt/NOTE] [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 nt/likes pizzas t/friend t/colleague`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [nt/NOTE] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`
**Help** | `help`
