(ns matcher-starter.core
  (:require [org.clojars.cognesence.breadth-search.core :refer :all]
            [org.clojars.cognesence.matcher.core :refer :all]
            [org.clojars.cognesence.ops-search.core :refer :all]))

(def ops
  ;some platforms may be picked up
  '{pickup            {:pre ((agent ?agent)
                              (ladder ?lad)
                              (manipulable ?lad)
                              (at ?agent ?plat1 ?sub1)
                              (on ?lad ?plat1 ?sub1)
                              )
                       :add ((holds ?agent ?lad))
                       :del ((on ?lad ?plat1 ?sub1)
                              (holds ?agent nil))
                       :txt (pickup ?lad from ?sub1)
                       :cmd [pickup ?lad]
                       }
    ;some platforms may be dropped
    drop              {:pre ((agent ?agent)
                              (ladder ?lad)
                              (manipulable ?lad)
                              (at ?agent ?plat1 ?sub1)
                              (holds ?agent ?lad)
                              )
                       :add ((holds ?agent nil)
                              (on ?lad ?plat1 ?sub1))
                       :del ((holds ?agent ?lad))
                       :txt (drop ?lad at ?sub1)
                       :cmd [drop ?lad]
                       }
    ;move between connected places
    move-to           {:pre ((agent ?agent)
                              (at ?agent ?p1 ?sub1)
                              (connects ?p1 ?p2)
                              (climbable ?p1 ?p2)
                              )
                       :add ((at ?agent ?p1 ?sub2))
                       :del ((at ?agent ?p1 ?sub1))
                       :txt (move ?agent from ?sub1 to ?sub2)
                       :cmd [move-to ?sub2]
                       }


    ;pick up from a platform
    pick-off           {:pre ((agent ?agent)
                               (obj ?obj)
                               (manipulable ?obj)
                               (holds ?agent nil)
                               (at ?agent ?plat1 ?sub1)
                               (on ?obj ?plat1 ?sub1)
                               )
                        :add ((holds ?agent ?obj))
                        :del ((on ?obj ?plat1 ?sub1)
                               (holds ?agent nil))
                        :txt (pickup ?obj from ?sub1)
                        :cmd [pick-off ?obj]
                        }
    ;drop on a platform
    drop-on         {:pre ((agent ?agent)
                            (obj ?obj)
                            (manipulable ?obj)
                            (holds ?agent ?obj)
                            (at ?agent ?plat1 ?sub1)
                            )
                     :add ((holds ?agent nil)
                            (on ?obj ?plat1 ?sub1))
                     :del ((holds ?agent ?obj))
                     :txt (drop ?obj at ?sub1)
                     :cmd [drop-on ?obj]
                     }


    climb-up-ladder   {:pre ((agent ?agent)
                              (ladder ?lad)
                              (at ?agent ?p1 ?sub1)
                              (climbable ?lad)
                              (connects ?sub1 ?sub2)
                              (section ?p2 ?sub2)
                              (on ?lad ?p1 ?sub1)
                              )
                       :add ((at ?agent ?p2 ?sub2))
                       :del (at ?agent ?p1 ?sub1)
                       :txt (climb-up-ladder ?p1 to ?p2)
                       :cmd [climb-up-ladder ?p2]
                       }

    climb-down-ladder {:pre ((agent ?agent)
                              (ladder ?lad)
                              (at ?agent ?p1 ?sub1)
                              (climbable ?lad)
                              (section ?p2 ?sub2)
                              (connects ?sub1 ?sub2)
                              (on ?lad ?p2 ?sub2)
                              )
                       :add ((at ?agent ?p2 ?sub2))
                       :del (at ?agent ?p1 ?sub1)
                       :txt (climb-down-ladder ?p1 to ?p2)
                       :cmd [climb-down-ladder ?p2]
                       }

    })

(def world
  '#{(agent matty)
     (ladder ladder)
     (obj book)
     (climbable p2 p3)
     (climbable p3 p2)
     (climbable p3 p3)
     (climbable p4 p3)
     (climbable ladder)
     (section p1 sub1)
     (section p1 sub2)
     (section p1 sub3)
     (section p2 sub4)
     (section p3 sub5)
     (section p4 sub6)
     (connects p1 p2)
     (connects p2 p3)
     (connects p3 p4)
     (connects sub4 sub1)
     (connects sub5 sub2)
     (connects sub6 sub3)
     (connects sub1 sub4)
     (connects sub2 sub5)
     (connects sub3 sub6)
     (connects sub1 sub2)
     (connects sub2 sub1)
     (connects sub2 sub3)
     (connects sub3 sub2)


     (manipulable ladder)
     (manipulable book)

     })

(def state1
  '#{(on ladder p1 sub2)
     (at matty p1 sub1)
     (on book p4 sub6)
     (holds matty nil)
     })

(def state2
  '#{(on ladder p1 sub2)
     (at matty p4 sub6)
     (obj book p1 sub3)
     (holds matty nil)
     })

(def state3
  '#{(on ladder p1 sub2)
     (at matty p2 sub4)
     (holds matty nil)
     })




;(ops-search state1 '((at matty p3 sub5)) ops :world world)

;(ops-search state1 '((at matty p2 sub4)) ops :world world)

;(ops-search state1 '((holds matty ladder)) ops :world world)

;(ops-search state1 '((holds matty book)) ops :world world)

;(ops-search state1 '((holds matty nil)) ops :world world)

;(ops-search state1 '((at matty p4 sub6)) ops :world world)