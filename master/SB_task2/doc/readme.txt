#Usage:

#For Space Implementation first start without args
space.SpaceServer

#Start gui with
factory.Factory (rmi|space)

#Start workers with (id_string is the choosen name of the worker, eg. 'imp1')
#Dwarfs need the checking-type of 0 or 1
worker.AssembleingImp id_string (rmi|space)
worker.TestingDwarf id_string (0|1) (rmi|space)
worker.LogisticReindeer id_string (rmi|space)


#e.g.
worker.AssembleingImp imp1 space
worker.TestingDwarf dwarf1 0 space
worker.LogisticReindeer deer1 space