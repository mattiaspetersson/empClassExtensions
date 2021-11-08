+ Buffer {

	*readAsWavetable { arg server, path, numChannels = 1, completionMessage, bufnum;
		var file, array;
		server = server ? Server.default;
		bufnum ?? { bufnum = server.nextBufferNumber(1) };
		file = SoundFile.openRead(path);
		array = FloatArray.newClear(file.numFrames);
		file.readData(array);
		file.close;
		array = array.as(Signal).asWavetable;
		^super.newCopyArgs(server,
			bufnum,
			file.numFrames * 2,
			numChannels)
		.alloc(completionMessage).sampleRate_(server.sampleRate).cache
		.loadCollection(array);
	}

	*readAsWavetables { arg server, path, numChannels = 1, completionMessage, bufnum, numWavetables = 1;
		var file, array, size;
		server = server ? Server.default;
		bufnum ?? { bufnum = server.nextBufferNumber(1) };
		file = SoundFile.openRead(path);
		//numChannels = file.numChannels; // the file should probably be mono anyway?
		size = (file.numFrames / numWavetables); //.nextPowerOfTwo;
		array = FloatArray.newClear(file.numFrames);
		file.readData(array);
		array = array.clump(size);
		file.close;
		^array = array.collect{|a, i|
			var buf;
			a = a.resamp1(size.nextPowerOfTwo);
			a.size.isPowerOfTwo.postln;
			a = a.as(FloatArray).as(Signal).asWavetable;
			buf = Buffer.new(server, a.size, numChannels, bufnum + i);
			server.sendMsg(\b_alloc, bufnum + i, a.size, numChannels, completionMessage.value(buf, i));
			buf.cache;
			buf.loadCollection(a);
			buf.normalize(1, true);
		};
	}
}