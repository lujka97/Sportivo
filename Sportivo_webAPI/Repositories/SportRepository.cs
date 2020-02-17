using Microsoft.EntityFrameworkCore;
using Sportivo_webAPI.DAL;
using Sportivo_webAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Sportivo_webAPI.Repositories
{
    public class SportRepository
    {
        public ICollection<Sport> GetAll()
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    var sports = context.Sports.ToList();
                    return sports;
                }
            }
            catch { return null; }
        }

        public ICollection<Sport> GetAllForCompany(int companyId)
        {
            try
            {
                using (var context = new SportivoContext(new DbContextOptions<SportivoContext>()))
                {
                    var courts = context.Courts.Where(c => c.CompanyId == companyId);

                    List<int> sportIds = new List<int>();
                    foreach (var court in courts)
                    {
                        if(!sportIds.Contains(court.SportId))
                            sportIds.Add(court.SportId);
                    }

                    var sports = context.Sports.Where(s => sportIds.Contains(s.SportId)).ToList();

                    return sports;
                }
            }
            catch { return null; }
        }
    }
}
