// To post way too many decimals just for show.
// ex: 1.33.postPrecision()
+ Float{
	postPrecision {|prec = 99|
		^this.asStringPrec(prec).postln.asFloat;
	}
}