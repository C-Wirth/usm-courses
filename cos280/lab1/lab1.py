'''
Author : Colby Wirth 
Version: 4 September 2024
COS 280
Professor James Quinlan
'''

#Exercise 1 - determine positive primes - returns 0 if not a positive prime
def isPrime(n : int):
    if n < 1 :
        return False
    
    if n == 1 or n == 2:
        return True
    
    for i in range(n-1):
        if n > 1 and n%i == 0:
            return True
        
    return False

print(isPrime(2))
