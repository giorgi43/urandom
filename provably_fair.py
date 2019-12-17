#/usr/bin/python3
import hashlib
import math

nBits = 52
houseEdge = 4

def calculate(server_seed, seed1, seed2, seed3):
	# Adding seeds
	mixed = server_seed + seed1 + seed2 + seed3

	# Hashing with SHA512
	hash_object = hashlib.sha512(bytes(mixed, encoding="ascii"))
	hashed = hash_object.hexdigest()

	# Get first nBits/4 char from hashed 
	hexed = hashed[0:int(nBits/4)]

	# Convert to decimal
	dec = str(int(hexed, 16))

	# Get x
	x = int(dec)/int(math.pow(2,nBits))
	x = math.floor((100 - houseEdge)/(1-x))

	# Get Result
	result = x/100
	return max(1,result)

if __name__ == '__main__':
	server_seed = input("server_seed: ")
	seed1 = input("seed1: ")
	seed2 = input("seed2: ")
	seed3 = input("seed3: ")

	print("Result = " + str(calculate(server_seed, seed1, seed2, seed3)))


