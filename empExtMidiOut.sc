// a newByName that actually works on Linux. Thanks to Ludvig Elblaus for pointing me in the right direction!
+ MIDIOut {
	*newByNameLinux {|deviceName, portName|
		var endPoint, firstOutput, midiOutPortNumber, alsaPortNumber;
		endPoint = MIDIOut.findPort(deviceName, portName);
		if(endPoint.notNil) {
			firstOutput = MIDIClient.destinations.select({ |destination| destination.device == "SuperCollider" }).size();
			midiOutPortNumber = MIDIClient.destinations.detectIndex({|destination, i| destination == endPoint});
			alsaPortNumber = "aconnect -o | awk '$0 ~ \"%\" {print $1}'".format(endPoint.name).unixCmdGetStdOut;
			"aconnect SuperCollider:% \"%:%\"".format(midiOutPortNumber + firstOutput, endPoint.device, alsaPortNumber).systemCmd;
			^MIDIOut.new(midiOutPortNumber);
		} {
			Error("Failed to find MIDIOut port " + deviceName + portName).throw;
		}
	}
}