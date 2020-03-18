using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using AnyThinkAds.ThirdParty.MiniJSON;

namespace AnyThinkAds.Api
{
    public class ATCallbackInfo
    {

        public readonly int network_firm_id;
        public readonly string adsource_id;
        public readonly int adsource_index;
        public readonly double adsource_price;
        public readonly int adsource_isheaderbidding;

        public readonly string id;
        public readonly double publisher_revenue;
        public readonly string currency;
        public readonly string country;
        public readonly string adunit_id;

        public readonly string adunit_format;
        public readonly string precision;
        public readonly string network_type;
        public readonly string network_placement_id;
        public readonly int ecpm_level;

        public readonly int segment_id;
        public readonly string scenario_id;
        public readonly string scenario_reward_name;
        public readonly int scenario_reward_number;
        public readonly string placement_reward_name;
        public readonly int placement_reward_number;

        public readonly string sub_channel;
        public readonly string channel;
        public readonly Dictionary<string, object> custom_rule;

        private string callbackJson;

        public ATCallbackInfo(string callbackJson)
        {
            try
            {
                this.callbackJson = callbackJson;
                Dictionary<string, object> jsonObjects = Json.Deserialize(callbackJson) as Dictionary<string, object>;
                network_firm_id = System.Convert.ToInt32(jsonObjects.ContainsKey("network_firm_id") ? jsonObjects["network_firm_id"] : "0");
                adsource_id = jsonObjects.ContainsKey("adsource_id") ? jsonObjects["adsource_id"] as string : "";
                adsource_index = System.Convert.ToInt32(jsonObjects.ContainsKey("adsource_index") ? jsonObjects["adsource_index"] : "-1");
                adsource_price = System.Convert.ToDouble(jsonObjects.ContainsKey("adsource_price") ? jsonObjects["adsource_price"] : "0");
                adsource_isheaderbidding = System.Convert.ToInt32(jsonObjects.ContainsKey("adsource_isheaderbidding") ? jsonObjects["adsource_isheaderbidding"] : "0");


                id = jsonObjects.ContainsKey("id") ? jsonObjects["id"] as string : "";
                publisher_revenue = System.Convert.ToDouble(jsonObjects.ContainsKey("publisher_revenue") ? jsonObjects["publisher_revenue"] : "0");
                currency = jsonObjects.ContainsKey("currency") ? jsonObjects["currency"] as string : "";
                country = jsonObjects.ContainsKey("country") ? jsonObjects["country"] as string : "";

                adunit_format = jsonObjects.ContainsKey("adunit_format") ? jsonObjects["adunit_format"] as string : "";
                adunit_id = jsonObjects.ContainsKey("adunit_id") ? jsonObjects["adunit_id"] as string : "";

                precision = jsonObjects.ContainsKey("precision") ? jsonObjects["precision"] as string : "";

                network_type = jsonObjects.ContainsKey("network_type") ? jsonObjects["network_type"] as string : "";

                network_placement_id = jsonObjects.ContainsKey("network_placement_id") ? jsonObjects["network_placement_id"] as string : "";
                ecpm_level = System.Convert.ToInt32(jsonObjects.ContainsKey("ecpm_level") ? jsonObjects["ecpm_level"] : "0");
                segment_id = System.Convert.ToInt32(jsonObjects.ContainsKey("segment_id") ? jsonObjects["segment_id"] : "0");
                scenario_id = jsonObjects.ContainsKey("scenario_id") ? jsonObjects["scenario_id"] as string : "";// RewardVideo & Interstitial

                scenario_reward_name = jsonObjects.ContainsKey("scenario_reward_name") ? jsonObjects["scenario_reward_name"] as string : "";
                scenario_reward_number = System.Convert.ToInt32(jsonObjects.ContainsKey("scenario_reward_number") ? jsonObjects["scenario_reward_number"] : "0");
                placement_reward_name = jsonObjects.ContainsKey("placement_reward_name") ? jsonObjects["placement_reward_name"] as string : "";
                placement_reward_number = System.Convert.ToInt32(jsonObjects.ContainsKey("placement_reward_number") ? jsonObjects["placement_reward_number"] : "0");

                channel = jsonObjects.ContainsKey("channel") ? jsonObjects["channel"] as string : "";
                sub_channel = jsonObjects.ContainsKey("sub_channel") ? jsonObjects["sub_channel"] as string : "";
                custom_rule = jsonObjects["custom_rule"] as Dictionary<string, object>;
                //custom_rule = jsonObjects.ContainsKey("custom_rule")? Json.Deserialize(jsonObjects["custom_rule"] as string) as Dictionary<string,object>:null;
            }
            catch (System.Exception e) { }
        }

        public string getOriginJSONString()
                {
            return callbackJson;
        }

        public Dictionary<string, object> toDictionary()
        {
            Dictionary<string, object> dataDictionary = new Dictionary<string, object>();

            dataDictionary.Add("network_firm_id",network_firm_id);
            dataDictionary.Add("adsource_id", adsource_id);
            dataDictionary.Add("adsource_index", adsource_index);
            dataDictionary.Add("adsource_price", adsource_price);
            dataDictionary.Add("adsource_isheaderbidding", adsource_isheaderbidding);
            dataDictionary.Add("id", id);
            dataDictionary.Add("publisher_revenue", publisher_revenue);
            dataDictionary.Add("currency", currency);
            dataDictionary.Add("country", country);
            dataDictionary.Add("adunit_id", adunit_id);
            dataDictionary.Add("adunit_format", adunit_format);
            dataDictionary.Add("precision", precision);
            dataDictionary.Add("network_type", network_type);
            dataDictionary.Add("network_placement_id",network_placement_id);
            dataDictionary.Add("ecpm_level", ecpm_level);
            dataDictionary.Add("segment_id", segment_id);
            dataDictionary.Add("scenario_id", scenario_id);
            dataDictionary.Add("scenario_reward_name", scenario_reward_name);
            dataDictionary.Add("scenario_reward_number", scenario_reward_number);

            dataDictionary.Add("placement_reward_name", placement_reward_name);
            dataDictionary.Add("placement_reward_number", placement_reward_number);
            dataDictionary.Add("sub_channel", sub_channel);
            dataDictionary.Add("channel", channel);
            dataDictionary.Add("custom_rule", custom_rule);
            return dataDictionary;
        }


    }
}
