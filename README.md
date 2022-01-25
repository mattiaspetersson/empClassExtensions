# empClassExtensions
This is some class extensions i've used now and then.

The Buffer extensions include two class methods, simplifying the preparation of sound files to be used as wavetables:
readAsWavetable returns a Buffer loaded with a (mono) file, converted into the wavetable format.
readAsWavetables slices a sound file into an arbitrary number of chunkc and returns an array of consecutive Buffers containing these slices in the wavetable format. This is useful with VOsc or VOsc3 for example.

extInteger includes the method asMantaLedColor and is intended for control of the LED's on a Snyderphonics Manta controller.

extFloat includes a stupid method to increase the number of decimals posted in the Post Window. Not particularly useful, but looks cool.

extMIDIOut includes the newByNameLinux method. It's a hack to solve the very strange MIDI implemetation on Linux systems, and to be able to create MIDIOuts as the documentation says newByName should work.
