using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace Sportivo_webAPI.Models
{
    public class Company
    {
        public int CompanyId { get; set; }
        [Required]
        public string CompanyName { get; set; }
        public double? Latitude { get; set; }
        public double? Longitude { get; set; }
        public string PhoneNumber { get; set; }
        public string Description { get; set; }
        public ICollection<Court> Courts { get; set; }
        public int Opens { get; set; }
        public int Closes { get; set; }
    }
}
