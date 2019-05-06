//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.2
//
// <auto-generated>
//
// Generated from file `BankClient.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Java.Utils.BankClient;

public class CreditInfo implements java.lang.Cloneable,
                                   java.io.Serializable
{
    public Currency localCurrency;

    public Currency creditCurrency;

    public double localCurrencyCost;

    public double creditCurrencyCost;

    public CreditInfo()
    {
        this.localCurrency = Currency.PLN;
        this.creditCurrency = Currency.PLN;
    }

    public CreditInfo(Currency localCurrency, Currency creditCurrency, double localCurrencyCost, double creditCurrencyCost)
    {
        this.localCurrency = localCurrency;
        this.creditCurrency = creditCurrency;
        this.localCurrencyCost = localCurrencyCost;
        this.creditCurrencyCost = creditCurrencyCost;
    }

    public boolean equals(java.lang.Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        CreditInfo r = null;
        if(rhs instanceof CreditInfo)
        {
            r = (CreditInfo)rhs;
        }

        if(r != null)
        {
            if(this.localCurrency != r.localCurrency)
            {
                if(this.localCurrency == null || r.localCurrency == null || !this.localCurrency.equals(r.localCurrency))
                {
                    return false;
                }
            }
            if(this.creditCurrency != r.creditCurrency)
            {
                if(this.creditCurrency == null || r.creditCurrency == null || !this.creditCurrency.equals(r.creditCurrency))
                {
                    return false;
                }
            }
            if(this.localCurrencyCost != r.localCurrencyCost)
            {
                return false;
            }
            if(this.creditCurrencyCost != r.creditCurrencyCost)
            {
                return false;
            }

            return true;
        }

        return false;
    }

    public int hashCode()
    {
        int h_ = 5381;
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::BankClient::CreditInfo");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, localCurrency);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, creditCurrency);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, localCurrencyCost);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, creditCurrencyCost);
        return h_;
    }

    public CreditInfo clone()
    {
        CreditInfo c = null;
        try
        {
            c = (CreditInfo)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return c;
    }

    public void ice_writeMembers(com.zeroc.Ice.OutputStream ostr)
    {
        Currency.ice_write(ostr, this.localCurrency);
        Currency.ice_write(ostr, this.creditCurrency);
        ostr.writeDouble(this.localCurrencyCost);
        ostr.writeDouble(this.creditCurrencyCost);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.localCurrency = Currency.ice_read(istr);
        this.creditCurrency = Currency.ice_read(istr);
        this.localCurrencyCost = istr.readDouble();
        this.creditCurrencyCost = istr.readDouble();
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, CreditInfo v)
    {
        if(v == null)
        {
            _nullMarshalValue.ice_writeMembers(ostr);
        }
        else
        {
            v.ice_writeMembers(ostr);
        }
    }

    static public CreditInfo ice_read(com.zeroc.Ice.InputStream istr)
    {
        CreditInfo v = new CreditInfo();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<CreditInfo> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, CreditInfo v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            ice_write(ostr, v);
            ostr.endSize(pos);
        }
    }

    static public java.util.Optional<CreditInfo> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            return java.util.Optional.of(CreditInfo.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final CreditInfo _nullMarshalValue = new CreditInfo();

    /** @hidden */
    public static final long serialVersionUID = -2034402248885081161L;
}
