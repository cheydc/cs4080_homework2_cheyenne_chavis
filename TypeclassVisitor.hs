{-# LANGUAGE ExistentialQuantification #-}

-- The “bundle of operations”:
class LoxVal a where
  showV   :: a -> String
  truthy  :: a -> Bool
  addV    :: a -> a -> a        -- whatever ops you want to support

-- New types are easy: just add a datatype + an instance.
newtype VNum = VNum Double
instance LoxVal VNum where
  showV (VNum n) = show n
  truthy (VNum n) = n /= 0
  addV (VNum a) (VNum b) = VNum (a + b)

newtype VStr = VStr String
instance LoxVal VStr where
  showV (VStr s) = s
  truthy (VStr s) = not (null s)
  addV (VStr a) (VStr b) = VStr (a ++ b)

-- If you want a single “Value” container:
data Value = forall a. LoxVal a => Value a

showValue :: Value -> String
showValue (Value x) = showV x