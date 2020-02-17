using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Sportivo_webAPI.Models
{
    public class Court
    {
        public int CourtId { get; set; }
        public string CourtName { get; set; }
        public Company Company { get; set; }
        public int CompanyId { get; set; }
        public Sport Sport { get; set; }
        public int SportId { get; set; }
        public int Price { get; set; }
        public ICollection<Reservation> Reservations { get; set; }
    }
}
